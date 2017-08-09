var db = null;
var conf = {
    IGNORE_ALERTS: false,
    DROP_TABLE_ENABLED: false,
    ALLOW_DEBUG: true
};
ALLOW_ANY_ACTION = null;
getToday = function () {
    var split = new Date().toLocaleDateString().split('.');
    var ret = '';
    ret += split[2] + '-' + split[1] + '-' + split[0];
    return ret;
}
connectToDB = function () {
    db = window.openDatabase('awesome_notes', '1.0',
        'AwesomeNotes Database', 1024 * 1024 * 3);
};

myalert = function (al) {
    al = al || 'Coś poszło bardzo nie tak !';
    if (!conf.IGNORE_ALERTS) {
        alert(al);
    }
    console && console.log(al);
};
err_callback = function (p1, p2) {

    myalert(p2.message);
};
createNotesTable = function () {
    if (conf.DROP_TABLE_ENABLED === true) {

        db.transaction(function (tx) {
            tx.executeSql(
                "DROP TABLE notes", [],
                function () {
                    myalert('Wyczyszczono bazę!');
                }, err_callback
            );
        });
    }
    db.transaction(function (tx) {
        tx.executeSql(
            "CREATE TABLE IF NOT EXISTS notes (id INTEGER PRIMARY KEY, name TEXT NOT NULL ,day TEXT, duration INTEGER,type INTEGER,isPlanned INTEGER)", [],
            function () {
                myalert('Baza notatek została utworzona!');
            }, err_callback
        );
    });
};
//( name  ,day , duration ,type ,isPlanned )

insertBasicNote = function (params) {
    var name = params.name;
    var duration = params.duration;
    var type = params.type;
    db.transaction(function (tx) {
        tx.executeSql("INSERT INTO notes (name, isPlanned,duration,type) VALUES (?, ?,?,?)",
            [name, 0, duration, type],
            function (tx, result) {
                var id = result.insertId;
                myalert('Zadanie '+name+' zostało zapisane!');
                addToNotPlannedTasksList(id, name, duration, type);

            }, err_callback
        );
    });
};
updateBasicNote = function (params) {
    var name = params.name;
    var duration = params.duration;
    var type = params.type;
    var id = params.id;
    db.transaction(function (tx) {
        tx.executeSql("UPDATE notes SET name = ?, duration=?, type=?  WHERE id = ?",
            [name, duration, type, id],
            function (tx, result) {
                myalert('Zadanie '+name+' zostało zmienione!');
                var tr = $("#" + id);
                tr.attr("class", getCssForDurationAndType(duration, type));
                tr.find("td").html(name);
            }, err_callback
        );
    });
};

function updatePlannedNote(params) {
    var name = params.name;
    var duration = params.duration;
    var type = params.type;
    var id = params.id;
    var currentInd = params.currentInd + 1;
    deleteAnyNote(id, true);
    db.transaction(function (tx) {
        tx.executeSql("INSERT INTO notes ( name  ,day , duration ,type ,isPlanned) VALUES (?,?,?,?,?) ",
            [name, getSelectedDate(), duration, type, currentInd],
            function (tx, result) {
                myalert('Zadanie '+name+' zostało zmienione!');
                var tr = $("#" + id);
                tr.attr("class", getCssForDurationAndType(duration, type));
                tr.attr("id", result.insertId);
                tr.find("td").html(name);
            }, err_callback
        );
    });
}

function insertPlannedNote(params) {
    /*
     od biedy mogloby zostac tamto
     */

    var name = params.name;
    var duration = params.duration;
    var type = params.type;
    var id = params.id;
    var currentInd = params.currentInd + 1;

    db.transaction(function (tx) {
        tx.executeSql("INSERT INTO notes ( name  ,day , duration ,type ,isPlanned) VALUES (?,?,?,?,?) ",
            [name, getSelectedDate(), duration, type, currentInd],
            function (tx, result) {

                myalert('Rekord numer ' + currentInd + ' został przeniesiony!');
                var tr = $("#" + id);
                tr.attr("class", getCssForDurationAndType(duration, type));
                tr.attr("id", result.insertId);
                tr.find("td").html(name);
            }, err_callback
        );
    });
}


deleteAnyNote = function (id, deleteQuietly) {
    var deleteQuietly = deleteQuietly;
    db.transaction(function (tx) {
        tx.executeSql("DELETE FROM notes WHERE id = ?", [id],
            function (tx, result) {
                if (deleteQuietly === true) {
                    return;
                }
                myalert('Zadanie '+name+' został usunięty!');
                var item = document.getElementById(id);
                item.hidden = true;
                item.draggable = false;
            }, err_callback
        );
    });
};


fetchBasicNotes = function () {
    db.transaction(function (tx) {
        tx.executeSql('SELECT id , name, duration ,type  FROM notes WHERE isPlanned=0', [],
            function (SQLTransaction, data) {
                //_.each
                for (var i = 0; i < data.rows.length; ++i) {
                    var row = data.rows.item(i);
                    var id = row['id'];
                    var name = row['name'];
                    var duration = row['duration'];
                    var type = row['type'];

                    addToNotPlannedTasksList(id, name, duration, type);
                }
            }, err_callback);
    });
};

fetchBasicNotesForDay = function () {
    db.transaction(function (tx) {
        tx.executeSql('SELECT id , name ,day , duration ,type,isPlanned  FROM notes WHERE isPlanned>0 AND day=? ORDER BY isPlanned', [getSelectedDate()],
            function (SQLTransaction, data) {
                //_.each
                for (var i = 0; i < data.rows.length; ++i) {
                    var row = data.rows.item(i);
                    var id = row['id'];
                    var name = row['name'];
                    var day = row['day'];
                    var duration = row['duration'];
                    var type = row['type'];

                    addToPlannedTasksList(id, name, duration, type);
                }
            }, err_callback);
    });
};

// Dodanie do listy notatek pozycji o danym id i tytule

function getCssForDurationAndType(duration, type) {
    var getType = function (type) {
        switch (type) {
            case 1:
                return "warning";
            case 2:
                return "info";
            case 3:
                return "danger";
            default :
//                myalert("getCssForDurationAndType - error");
                return "success";
        }
    }
    var getDurationClass = function (duration) {

        if (isDurationNotBetween1and4(duration)) {
//            myalert("getCssForDurationAndType - error");
            return "";
        } else {
            return "tr-h-" + duration;
        }
    }

    var ret = getType(type) + " " + getDurationClass(duration);
    console && console.log(ret);
    return  ret;
}
function getTaskTypeFromClass(classs) {
    classs = classs.split(" ")[0];
    switch (classs) {
        case "warning" :
            return 1;
        case "info" :
            return 2;
        case "danger" :
            return 3;
    }
//    myalert("getTaskTypeFromClass - error");
    return -1;
};


var getTaskType = function () {
    if ($("#type_1").is(":checked")) {
        return 1;
    }
    if ($("#type_2").is(":checked")) {
        return 2;
    }
    if ($("#type_3").is(":checked")) {
        return 3;
    }
//    myalert("getTaskType - error");
    return -1;
};


function prepareTdFromParams(name, id, duration, type) {
    var td = $("<td></td>");
//    td.attr("draggable", "true");
//    td.attr("ondragstart", "drag(event)");
    td.html(name);

    var tr = $("<tr></tr>");
    tr.attr("draggable", "true");
    tr.attr("ondragstart", "drag(event)");
    tr.attr("ondblclick", "show_pop_up_window(event)");
    tr.attr("id", id);
    tr.attr("class", getCssForDurationAndType(duration, type));
    tr.append(td);
    return tr;
}
addToNotPlannedTasksList = function (id, name, duration, type) {
    var tr = prepareTdFromParams(name, id, duration, type);
    $("#not_planned_tbody").append(tr);
};
addToPlannedTasksList = function (id, name, duration, type) {
    var tr = prepareTdFromParams(name, id, duration, type);
    $("#planned_tbody").append(tr);
};


function updatePopUpWindow(name, duration, type, id, plannedTableClicked) {
    $("#task_name").val(name);
    $("#task_duration").val(duration);
    $("#current_note_id").val(id);
    $("#planned_table_clicked").val(plannedTableClicked);
    for (var i = 1; i <= 3; i++) {
        $("#type_" + i).attr('checked', false);
    }
    $("#type_" + type)[0].checked = true;
}
function getDurationFromClass(attribute) {

    return attribute.split('h-')[1];
}


function getParamsFromRowForCurrentTarget(currentTarget) {
    var tr;
    var td;

    var name;
    var duration = 1;
    var type;
    var id;
    var currentInd;
    var plannedTableClicked;

    if (currentTarget.tagName === "TR" || currentTarget.tagName === "tr") {
        tr = currentTarget;
        td = currentTarget.children[0];
    } else {
        tr = currentTarget.parentNode;
        td = currentTarget;
    }

    name = td.innerHTML;
    id = tr.getAttribute("id")
    type = getTaskTypeFromClass(tr.getAttribute("class"));
    duration = getDurationFromClass(tr.getAttribute("class"));

    plannedTableClicked = isPlannedTabledClicked(currentTarget);
//    if (plannedTableClicked) {
    currentInd = getCurrentIndex(id);
//    }

    return {name: name, duration: duration, type: type, id: id, plannedTableClicked: plannedTableClicked, currentInd: currentInd};
}
function getParamsFromRowForEvent(event) {
    var currentTarget = event.currentTarget;
    return getParamsFromRowForCurrentTarget(currentTarget);
}
function getParamsFromRowForId(id) {
    var currentTarget = $("#" + id)[0];
    return getParamsFromRowForCurrentTarget(currentTarget);
}
var show_pop_up_window = function (event) {
    $('#myTex').show();
    if (!event || event.currentTarget.id === "new_button") {
        $("#save_button").attr('disabled', false);
        $("#change_button").attr('disabled', true);
        $("#delete_button").attr('disabled', true);
        updatePopUpWindow("", "", 1);
        return;
    }

    var params = getParamsFromRowForEvent(event);
    var plannedTableClicked = params.plannedTableClicked;
    if (plannedTableClicked) {
        $("#save_button").attr('disabled', true);
        $("#change_button").attr('disabled', false);
        $("#delete_button").attr('disabled', true);
    } else {
        $("#save_button").attr('disabled', true);
        $("#change_button").attr('disabled', false);
        $("#delete_button").attr('disabled', false);
    }

    var name = params.name;
    var duration = params.duration;
    var type = params.type;
    var id = params.id;
    updatePopUpWindow(name, duration, type, id, plannedTableClicked);

};


function isDurationNotBetween1and4(duration) {
    return duration === "" || (
        ( duration !== "1" && duration !== "2" && duration !== "3" && duration !== "4")
        &&
        ( duration !== 1 && duration !== 2 && duration !== 3 && duration !== 4)
        );
}
function getParamsFromPopOutWindow() {
    var name = $("#task_name").val();
    var duration = $("#task_duration").val();
    var plannedTableClicked = $("#planned_table_clicked").val();
    var id = $("#current_note_id").val();
    var type = getTaskType();
    var currentInd = getCurrentIndex(id);
    if (name === "") {
        myalert("Nazwa zadania nie może być pusta");
        return false;
    }
    if (isDurationNotBetween1and4(duration)) {
        myalert("Wybierz liczbę godzin spośród 1,2,3,4");
        return false;
    }
    if (!$("#type_1").is(":checked") && !$("#type_2").is(":checked") && !$("#type_3").is(":checked")) {
        myalert("Musisz wybrać typ zadania");
        return false;
    }
    return {name: name, duration: duration, type: type, id: id, plannedTableClicked: plannedTableClicked, currentInd: currentInd};
}
function getSelectedDate() {
    return $("#datepicker").val() || myalert('datepicker powinien miec juz date ustawiona a nie ma!');
}
refreshMyPage = function () {
    $("#datepicker").val() || $("#datepicker").val(getToday());
    ALLOW_ANY_ACTION = getToday() <= getSelectedDate();
    $("#planned_tbody").empty();
    $("#not_planned_tbody").empty();
    fetchBasicNotes();
    fetchBasicNotesForDay();
    if (ALLOW_ANY_ACTION === true) {
        $("#new_button").attr("disabled", false);
    } else {
        $("#new_button").attr("disabled", true);
    }
}
init = function () {
    connectToDB();
    createNotesTable();
    refreshMyPage();
};
$(function () {
    init();
    on_save_button = function (event) {
        event && event.preventDefault();
        var params = getParamsFromPopOutWindow();
        if (!params) {
            return;
        }
        insertBasicNote(params);
        $('#myTex').hide();
    };
    $("#new_button").click(show_pop_up_window);
    on_change_button = function (event) {
        event && event.preventDefault();

        var params = getParamsFromPopOutWindow();
        if (!params) {
            return;
        }
        if (params.plannedTableClicked === true || params.plannedTableClicked === "true") {
            updatePlannedNote(params);

        } else {
            updateBasicNote(params);

        }
        $('#myTex').hide();
    };
    on_delete_button = function (event) {
        event && event.preventDefault();
        var params = getParamsFromPopOutWindow();
        deleteAnyNote(params.id);
        $('#myTex').hide();
    };
    on_cancel_button = function (event) {
        event && event.preventDefault();
        $('#myTex').hide();
        getPlannedCount();
    };

    $("#datepicker").datepicker({
//        dateFormat: "dd-mm-yy",
        dateFormat: "yy-mm-dd",
        onSelect: function (t, a) {
            refreshMyPage();
        },
        onClose: function (t, a) {
        }
    });

    /*
     SO hacking
     $('.date').datepicker();
     $('#ui-datepicker-div').css('clip', 'auto');
     */
    $('#ui-datepicker-div').removeClass('ui-helper-hidden-accessible');
    $('#ui-datepicker-div').css('clip', 'auto');
});

/*
 1. zapis przy braku edycji w planowanych //da si wybronic
 ##4 disable when date ...
 2. dlugość wysokość
 3. pop up menu
 4. zabezpieczenie z not do not

 */