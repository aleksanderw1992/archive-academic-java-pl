var myTime = new Date();
var element = document.getElementById("czas");
var myMonth = "(nieznany)";  //wartość domyślna

var showDragWarn = true;
function warn_cannot_drag() {
    if (showDragWarn === true) {
        myalert('Nie możesz planować dnia wcześniejszego niż data dzisiejsza !');
        showDragWarn = false;
    }
}
function allowDrop(ev) {
    ev.preventDefault();

    if (ALLOW_ANY_ACTION === false) {
        warn_cannot_drag();
        return false;
    }
}

function drag(ev) {
    ev.dataTransfer.setData("outerHtml", ev.target.outerHTML);
    ev.dataTransfer.setData("myId", ev.target.id);
    showDragWarn = true;
}

function getTbodyFromAnyChild(target) {
    var table = target;
    while (table.tagName !== "table" && table.tagName !== "TABLE") {
        table = table.parentElement;
    }
    return table.tBodies[0];
};
function getPlannedCount() {
    return $("#planned_tbody").children().length;
};
function getCurrentIndex(id) {

    var trs = $("#planned_tbody").children();
    for (var i = 0; i < trs.length; i++) {
        if (trs[i].id === id) {
            return i;
        }
    }
//    myalert("getCurrentIndex");
    return getPlannedCount();
};
function isPlannedTabledClicked(anyChild) {
    var table = anyChild;
    while (table.tagName !== "table" && table.tagName !== "TABLE") {
        table = table.parentElement;
    }
    return table.id === "planned_task_table";
};

function drop(ev) {
    ev.preventDefault();
    if (ALLOW_ANY_ACTION === true) {
        var outerHTML = ev.dataTransfer.getData("outerHTML");
        var myId = ev.dataTransfer.getData("myId");
        var table_des = getTbodyFromAnyChild(ev.target);
//            var table_src = getTbodyFromAnyChild(ev.srcElement);
//            var src_isPlanned=

        var des_isPlanned = table_des.id === "planned_tbody";
        if (des_isPlanned === true) {
            var params = getParamsFromRowForId(myId);
            insertPlannedNote(params);
        } else {

            deleteAnyNote(myId, true);
        }
        table_des.appendChild(document.getElementById(myId));

    } else {
        warn_cannot_drag();
    }
//           var obj = document.getElementById(data);
//           obj.textContent = obj.innerText;
}

function addZero(i) {
    return (i < 10) ? "0" + i : i;
}

function writeDownDate() {
    document.write(Date());
}

function wyswietlMiesiac() {
    switch (myTime.getMonth() + 1) { 	//styczeń ma numer 0
        case 1:
            myMonth = "stycznia";
            break;
        case 2:
            myMonth = "lutego";
            break;
        case 3:
            myMonth = "marca";
            break;
        case 4:
            myMonth = "kwietnia";
            break;
        case 5:
            myMonth = "maja";
            break;
        case 6:
            myMonth = "czerwca";
            break;
        case 7:
            myMonth = "lipca";
            break;
        case 8:
            myMonth = "sierpnia";
            break;
        case 9:
            myMonth = "września";
            break;
        case 10:
            myMonth = "października";
            break;
        case 11:
            myMonth = "listopada";
            break;
        case 12:
            myMonth = "grudnia";
            break;
    }
    return myMonth;
}
init = function () {

    element.innerHTML += "Dzisiaj jest " + addZero(myTime.getDate()) + " " + wyswietlMiesiac() + " " + myTime.getFullYear() + "<br>";
    element.innerHTML += "Jest " + addZero(myTime.getHours()) + ":" + addZero(myTime.getMinutes());
}
init();
