<!DOCTYPE html>
<html> 
<head> 
   <meta http-equiv="content-type" content="text/html; charset=UTF-8"/> 
   <title>Moderator Table</title> 
   
</head>
 
<TABLE ID="tblMods" border="1" STYLE="border width:1 orange dashed;background color:#F0E68C;table-row width:2;">
	<TR>
		
		<TH WIDTH="230">Moderator Name</TH>
		<TH WIDTH="100"></TH>
		
	</TR>
	
</TABLE>
<form name="modForm"  method="POST">
  New Moderator Name: <input type="text" name="fname" /><br />
  <input type="submit" value="Submit" onClick="addMod(this.form)"/>
</form>  


<body> 
	<script type='text/javascript' src='http://code.jquery.com/jquery-1.6.4.js'></script>
   <SCRIPT TYPE="text/javascript">
	  var count = "1";
	  init('tblMods');
	  
	  function init(in_tbl_name)
	  {
		  $.getJSON('/EventWebSite/moderators', fillTable);
	  }
	  
	  function fillTable(data)
	  {
		  var tr,td;
		  var tbody = document.getElementById("tblMods");
		  for(var i=0; i < data.Moderators.length; i++)
		  {
			  	var Mod = data.Moderators[i];
			  	tr = tbody.insertRow(tbody.rows.length);
		        td = tr.insertCell(tr.cells.length);
		        td.setAttribute("align", "center");
		        td.innerHTML = Mod.name;
		        td = tr.insertCell(tr.cells.length);
		        td.innerHTML = "<INPUT TYPE=\"Button\" CLASS=\"Button\" onClick=\"remove(this)\" VALUE=\"Delete Row\">";
			  	
		  }
	  }
	  
	 
	 
	  
	  function addRow(in_tbl_name)
	  {
	    var tbody = document.getElementById(in_tbl_name).getElementsByTagName("TBODY")[0];
	    // create row
	    var row = document.createElement("TR");
	    // create table cell for Name
	    var td1 = document.createElement("TD")
	    var strHtml1 = "<INPUT TYPE=\"text\" NAME=\"in_name\" SIZE=\"30\" MAXLENGTH=\"30\"  STYLE=\"height:24;border: 1 solid;margin:0;\">";
	    td1.innerHTML = strHtml1.replace(/!count!/g,count);
	 	// create table cell for adding mod
	    var td2 = document.createElement("TD")
	    var strHtml2 = "<INPUT TYPE=\"Button\" CLASS=\"Button\" onClick=\"addMod(this)\" VALUE=\"Add to Mods\">";
	    td2.innerHTML = strHtml2.replace(/!count!/g,count);
	    // create table cell for deleting mod
	    var td3 = document.createElement("TD")
	    var strHtml3 = "<INPUT TYPE=\"Button\" CLASS=\"Button\" onClick=\"remove(this)\" VALUE=\"Delete Mod\">";
	    td3.innerHTML = strHtml3.replace(/!count!/g,count);
	    // append data to row
	    row.appendChild(td1);
	    row.appendChild(td2);
	    row.appendChild(td3);
	   
	    // add to count variable
	    count = parseInt(count) + 1;
	    // append row to table
	    tbody.appendChild(row);
	  }
	 
	  
	  function remove(src)
	  {
	  	var rowd = src.parentNode.parentNode.rowIndex;
	  	var table = document.getElementById("tblMods");
	  	var row = table.rows[rowd];
	  	var cell = row.cells[0];
	  	var name = cell.firstChild.nodeValue;
	  	$.post("moderators?removename="+name);
	  	table.deleteRow(rowd);
	  } 
	  
	  function addMod(form)
	  {
		  var name = form.fname.value;
		  $.post("moderators?addname="+name);
		  
		  var tbody = document.getElementById("tblMods").getElementsByTagName("TBODY")[0];
		  var row = document.createElement("TR");
		  var td1 = document.createElement("TD");
		  //td1.setAttribute("align", "center");
		  td1.innerHTML = name;
		  var td2 = document.createElement("TD");
		  td2.innerHTML = "<INPUT TYPE=\"Button\" CLASS=\"Button\" onClick=\"remove(this)\" VALUE=\"Delete Row\">";
		  row.appendChild(td1);
		  row.appendChild(td2);
		  tbody.appendChild(row);
	  }
</SCRIPT>
</body> 

</html>
