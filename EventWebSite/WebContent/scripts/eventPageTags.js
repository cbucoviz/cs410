/***********************************************
		* DD Tab Menu II script- � Dynamic Drive DHTML code library (www.dynamicdrive.com)
		* This notice MUST stay intact for legal use
		* Visit Dynamic Drive at http://www.dynamicdrive.com/ for full source code
		***********************************************/
		
		//Set tab to intially be selected when page loads:
		//[which tab (1=first tab), ID of tab content to display (or "" if no corresponding tab content)]:
		//var initialtab=[1, "city_event_tab"];
		var initialtab=[1, "event_info_tab"];
		//Turn menu into single level image tabs (completely hides 2nd level)?
		var turntosingle=0; //0 for no (default), 1 for yes
		
		//Disable hyperlinks in 1st level tab images?
		var disabletablinks=0; //0 for no (default), 1 for yes
		
		
		////////Stop editting////////////////
		
		var previoustab="event_info_tab";
		
		if (turntosingle==1)
		{
			document.write('<style type="text/css">\n#event_tab_container{display: none;}\n</style>');
		}
		
		function expandcontent(cid, aobject){
			if (disabletablinks==1)
			{
				aobject.onclick=new Function("return false");
			}
			
			if (document.getElementById && turntosingle==0){
				highlighttab(aobject);
				if (previoustab!="")
				{
					document.getElementById(previoustab).style.display="none";
				}
				
				if (cid!=""){
					document.getElementById(cid).style.display="block";
					previoustab=cid;
				}
			}
		}
		
		function highlighttab(aobject){
			if (typeof tabobjlinks=="undefined")
			{
				collectddimagetabs();
			}
			for (i=0; i<tabobjlinks.length; i++)
			{
				tabobjlinks[i].className="";
			}
			aobject.className="current";
		}
		
		function collectddimagetabs(){
			var tabobj=document.getElementById("ddimagetabs");
			tabobjlinks=tabobj.getElementsByTagName("A");
		}
		
		function do_onload(){
			
			collectddimagetabs();
			expandcontent(initialtab[1], tabobjlinks[initialtab[0]-1]);
		}
		
if (window.addEventListener)
{			
			window.addEventListener("load", do_onload, false);
}
else if (window.attachEvent)
{
			window.attachEvent("onload", do_onload);
}
else if (document.getElementById)
{
			window.onload=do_onload;
}