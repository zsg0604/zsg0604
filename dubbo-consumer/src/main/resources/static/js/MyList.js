setType=function(value,row,index){
	return value.name;
}



setButton=function(value,row, index){
	return "<input type='button' value='删除' onclick='deleteCar("+row.id+")'>";
}

serchComputer=function(){
	$("#data").datagrid('load',{
		name:$("#name").val(),
		tid:$("#op").val(),
		startdate:$("#startdate").datebox('getValue'),
		enddate:$("#enddate").datebox('getValue')
		
	})
	
}

openDialog=function(){
	$("#myDialog").dialog('open');
}
saveBook=function(){
	$("#addForm").form('submit',{
		url:"./car/add.do",
		method:"post",
		onSubmit:function(){
			return $(this).form('validate');
			
		},
		success: function(){
			alert("操作成功！！");	
			
			$("#myDialog").dialog('close');
			$("#data").datagrid('reload');
			$("#showImg").attr("src","");
			$("#addForm").form('clear');
		}
	})
}
closeDialog=function(){
	$("#myDialog").dialog('close');
}
updateDialog=function(){
	var rows=$("#data").datagrid('getSelections');
	if (rows.length!=1) {
		$.messager.alert('警告','请选择一条需要修改的数据','warning'); 
		return ;
	}
	var r=rows[0];
	
	$("#myDialog").dialog('open').dialog('setTitle','信息修改');
	$("#addForm").form('load',r);
	$("#showImg").attr("src","./upload/"+r.img);
	url:"./car/add.do";
	$("#data").datagrid('reload');
}
deleteDialog=function(){
	var rows=$("#data").datagrid('getSelections');
	if (rows.length==0) {
		$.messager.alert('警告','请选择一条需要删除的数据','warning'); 
		return ;
	}
	var temp ="";
	for (var i = 0; i < rows.length; i++) {
		temp+=","+rows[i].id;
	}
	var ids=temp.substr(1);
	if (confirm("你确定要删除这"+rows.length+"条数据吗")) {
		$.ajax({
			url:"./car/delete.do",
			type:"post",
			data:{ids:ids},
			success:function(){
				alert("已删除")
				$("#data").datagrid('reload');
			}
		})
	}
}
deleteCar=function(ids){
	if (confirm("你确定要删除这条数据吗")) {
		$.ajax({
			url:"./car/delete.do",
			type:"post",
			data:{ids:ids},
			success:function(){
				alert("已删除")
				$("#data").datagrid('reload');
			}
		})
	}
}

