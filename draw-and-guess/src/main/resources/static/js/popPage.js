// 3、弹出窗口的事件：

function ShowAdd(boxName) {
    if (document.getElementById(boxName).style.display == "block") {
    }

    document.getElementById(boxName).style.position = "absolute";

    document.getElementById(boxName).style.display = "block";

    document.getElementById("divBg").style.display = "block";

}

// 4、关闭窗口的事件：

function CancelAdd(boxName) {
    document.getElementById(boxName).style.display = "none";

    document.getElementById("divBg").style.display = "none";

}
