<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link rel="stylesheet" href="config/default.css" type="text/css"></link>



</head>
<body>

<script language="JavaScript1.2">

/***********************************************
* Sliding Menu Bar Script- © Dynamic Drive (www.dynamicdrive.com)
* Visit http://www.dynamicdrive.com/ for full source code
* This notice must stay intact for use
***********************************************/

var slidemenu_width='300px' //specify width of menu (in pixels)
var slidemenu_reveal='40px' //specify amount that menu should protrude initially
var slidemenu_top='0px'   //specify vertical offset of menu on page

var ns4=document.layers?1:0
var ie4=document.all
var ns6=document.getElementById&&!document.all?1:0

if (ie4||ns6)
document.write('<div id="slidemenubar2" style="left:'+((parseInt(slidemenu_width)-parseInt(slidemenu_reveal))*-1)+'px; top:'+slidemenu_top+'; width:'+slidemenu_width+'" onMouseover="pull()" onMouseout="draw()">')
else if (ns4){
document.write('<style>\n#slidemenubar{\nwidth:'+slidemenu_width+';}\n<\/style>\n')
document.write('<layer id="slidemenubar" left=0 top='+slidemenu_top+' width='+slidemenu_width+' onMouseover="pull()" onMouseout="draw()" visibility=hide>')
}

document.write('<table>');
document.write('<tr>');
document.write('<td>');

var sitems=new Array()

///////////Edit below/////////////////////////////////

//siteitems[x]=["Item Text", "Optional URL associated with text"]

//***For Main Page Login ONLY
sitems[0]=["<h1><b>Welcome</b></h1>", ""]
sitems[1]=["", ""]
sitems[2]=["", ""]
sitems[3]=["<h2><b>Login:</b></h2>", ""]
sitems[4]=["<form action='Login' method='POST'><p class='indent' style='margin-top:-20px'>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Email: <input type='text' name='login_email' size='20'></p>", ""]
sitems[5]=["<h5><p id='save_login_indent'><input type='checkbox' name='save_login_checkbox'>Remember Me?</p></h5>", ""]
sitems[6]=["<p class='indent'>Password: <input type='password' name='login_password' size='20'></p>", ""]
sitems[7]=["<p id='login_button_indent'><input type='submit' value='Login'/></p></form>", ""]
sitems[8]=["<p class='indent'><u><i>Not a member yet? Sign up NOW!</i></u></p>", "register.jsp"]
sitems[9]=["", ""]
sitems[10]=["", ""]
sitems[11]=["***Mock City Page***", "citypage.jsp"]					//Debug use only...
sitems[12]=["", ""]
sitems[13]=["***Mock Event Page***", "eventpage.jsp"]					//Debug use only...
sitems[14]=["", ""]
sitems[15]=["***Mock Calendar Page***", "calendar.jsp"]					//Debug use only...
sitems[16]=["", ""]
sitems[17]=["Return to Home", "home.jsp"]
sitems[18]=["", ""]
sitems[19]=["About", ""]



//If you want the links to load in another frame/window, specify name of target (ie: target="_new")
var target=""

/////////////////////////////////////////////////////////

if (ie4||ns4||ns6){
for (i=0;i<sitems.length;i++){
if (sitems[i][1])
document.write('<a href="'+sitems[i][1]+'" target="'+target+'">')
document.write(sitems[i][0])
if (sitems[i][1])
document.write('</a>')
document.write('<br>\n')
}
}

function regenerate(){
window.location.reload()
}
function regenerate2(){
if (ns4){
document.slidemenubar.left=((parseInt(slidemenu_width)-parseInt(slidemenu_reveal))*-1)
document.slidemenubar.visibility="show"
setTimeout("window.onresize=regenerate",400)
}
}
window.onload=regenerate2

rightboundary=0
leftboundary=(parseInt(slidemenu_width)-parseInt(slidemenu_reveal))*-1

document.write('</td>');
document.write('<td style="width:40px;text-align:center;valign:middle">')
document.write('<font color="yellow"><big> >>> </big></font>');
document.write('</td>');
document.write('</tr>');
document.write('</table>');

if (ie4||ns6){
document.write('</div>')
themenu=(ns6)? document.getElementById("slidemenubar2").style : document.all.slidemenubar2.style
}
else if (ns4){
document.write('</layer>')
themenu=document.layers.slidemenubar
}

function pull(){
if (window.drawit)
clearInterval(drawit)
pullit=setInterval("pullengine()",10)
}
function draw(){
clearInterval(pullit)
drawit=setInterval("drawengine()",10)
}
function pullengine(){
if ((ie4||ns6)&&parseInt(themenu.left)<rightboundary)
themenu.left=parseInt(themenu.left)+10+"px"
else if(ns4&&themenu.left<rightboundary)
themenu.left+=10
else if (window.pullit){
themenu.left=0
clearInterval(pullit)
}
}

function drawengine(){
if ((ie4||ns6)&&parseInt(themenu.left)>leftboundary)
themenu.left=parseInt(themenu.left)-10+"px"
else if(ns4&&themenu.left>leftboundary)
themenu.left-=10
else if (window.drawit){
themenu.left=leftboundary
clearInterval(drawit)
}
}
</script>
	
</body>
</html>