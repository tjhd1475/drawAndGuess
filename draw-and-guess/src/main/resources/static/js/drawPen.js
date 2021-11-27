var canvas,pen;
canvas=document.getElementById('myCanvas');
pen=canvas.getContext('2d');//获取上下文环境，2d画图

pen.lineWidth=5;//画笔的粗细
pen.strokeStyle="black";//画笔颜色

var mousePress=false;//判断鼠标是否按下，初始值为没有按下
var last=null;//鼠标结束的点，初始为空
var isPen=true;

function reGet(){
    canvas=document.getElementById('myCanvas')
    pen=canvas.getContext('2d')
    pen.lineWidth=5;//画笔的粗细
    pen.strokeStyle="black";//画笔颜色
    mousePress=false;//判断鼠标是否按下，初始值为没有按下
    last=null;//鼠标结束的点，初始为空
    canvas.onmousedown=start;
    canvas.onmousemove=draw;
    canvas.onmouseup=finish;

}

function clear(){
    canvas.getContext("2d").clearRect(0,0,canvas.width,canvas.height)
}

function thin(){
    pen.lineWidth=5;
}
function mid(){
    pen.lineWidth=10;
}
function thick(){
    pen.lineWidth=20;
}

function yellow(){
    pen.strokeStyle="yellow";
}
function black(){
    pen.strokeStyle="black";
}
function red(){
    pen.strokeStyle="red";
}
function green(){
    pen.strokeStyle="green";
}
function blue(){
    pen.strokeStyle="blue";
}
function pink(){
    pen.strokeStyle="pink";
}
function orange(){
    pen.strokeStyle="orange";
}
function changeColor(color){
    pen.strokeStyle=color
}
function eraser(){
    if(isPen){
        pen.strokeStyle="#ffe4c4"
        pen.lineWidth=10;
        isPen=false;
        document.getElementById("eraser").style.backgroundImage="url('images/pen2.png')"
        document.getElementById("eraser").style.backgroundSize="65px"
    }else {
        pen.strokeStyle = document.getElementById("pickColor").value;
        isPen = true;
        document.getElementById("eraser").style.backgroundImage = "url('images/eraser.png')"
        document.getElementById("eraser").style.backgroundSize = "90px"
    }
}
//设置是否是橡皮
function setEraser(isEraser){
    isPen=isEraser;
    eraser();
}
function pos(event){//鼠标取得点
    var ex,ey;
    ex=event.pageX;
    ey=event.pageY;
    var dom=document.getElementById("myCanvas")
    absolutePos=getAbsolutePosition(dom)
    return{
        x:ex-absolutePos.left,
        y:ey-absolutePos.top
    }
}

function start(event){//开始绘图函数
    mousePress=true;//鼠标按下
}
function draw(event){//当鼠标按下且移动才会绘图
    if(!mousePress) return;
    var xy=pos(event);
    if(last!=null){//看结束点是否为空
        //不为空证明有开始点结束点，方才开始绘图
        pen.beginPath();
        pen.moveTo(last.x,last.y);//上次绘图停止的点
        pen.lineTo(xy.x,xy.y);//本次绘图的终点
        pen.stroke();
    }
    last=xy;//把本次绘图终点变为上次绘图的重点
    //再次绘图的时候就能从本次绘图终点开始
}
function finish (event) {
    mousePress=false;
    last=null;
}

function upload(event){
    var canvas=document.getElementById("myCanvas")
    var context=canvas.getContext("2d");
    var imageData = context.getImageData(0, 0, canvas.width, canvas.height);
    for(var i = 0; i < imageData.data.length; i += 4) {
        // 当该像素是透明的，则设置成白色
        if(imageData.data[i + 3] == 0) {
            imageData.data[i] = 255;
            imageData.data[i + 1] = 228;
            imageData.data[i + 2] = 196;
            imageData.data[i + 3] = 255;
        }
    }
    context.putImageData(imageData, 0, 0);

    var image=canvas.toDataURL("image/jpeg",1.0)
    return image;
}
// 获取canvas的base64图片的dataURL（图片格式为image/jpeg）
function getBase64(canvas, callback) {
    var dataURL = canvas.toDataURL("image/jpeg");
    if(typeof callback !== undefined) {
        callback(dataURL);
    }
}
function getAbsolutePosition(node) {
    var position = {
        left: 0,
        top : 0
    }
    do {
        position.top  += node.offsetTop;
        position.left += node.offsetLeft;
        node           = node.offsetParent;
    } while (node);
    return position;
}
canvas.onmousedown=start;
canvas.onmousemove=draw;
canvas.onmouseup=finish;
