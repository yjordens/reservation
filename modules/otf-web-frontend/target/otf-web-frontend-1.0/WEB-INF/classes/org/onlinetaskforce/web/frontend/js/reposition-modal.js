function getScroll() {

if (document.all) {
// We are in IE
return top.document.documentElement.scrollTop;
} else {
// In other browsers
return top.pageYOffset;
}
}


function fixWindowVertical() {
var myWindow=Wicket.Window.get();
if(myWindow) {
var top = getScroll() + 70;
myWindow.window.style.top = top + "px";
}
return false;
}