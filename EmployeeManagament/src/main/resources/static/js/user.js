listUsers=[];
loadTable();
$(window).load(function(){
	if(sessionStorage.getItem("role")==='ADMIN'){
		$.each($('.menu-navv'),function(i,tag){
			$(tag).prop('style','display:block;');
		});
	}
	$.each($('.username-wel'),function(i,tag){
		$(tag).text(sessionStorage.getItem("username"));
	});
	checkAll();
	$('#delete-all').on('click',function(){
		multiDelete();
	});	
});

function loadTable(){
	var listUser=[];
	$.ajax({
		url:'/api/users',
		type:'GET',
		success: function(data){
			listUsers=data;
			$.each(data,function(i,user){
				listItem=[];
				listItem.push('<div class="icheckbox_flat-green" style="position: relative;">'
						+'<input type="checkbox" value="'+user.id+'" name="check-element" class="flat checkbox-delete" style="position: absolute; opacity: 0;">'
						+'<ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255); border: 0px; opacity: 0;">'
						+'</ins></div>');
				listItem.push(user.id);
				listItem.push('<a onclick="viewUser('+user.id+')">'+user.email+'</a>');
				listItem.push(user.fullname);
				listItem.push((user.gender===true)?'Female':'Male');
				listItem.push(user.address);
				listItem.push(user.phone);
				listItem.push(
						'<img style="margin:0 10%;cursor:pointer" alt="delete Icon" src="/images/icon_trash.png" width="25px" height="25px" onclick="removeUser('+user.id+')" />' +
						'<a href="/admin/user/edit/' + user.id + '"><img style="margin:0 10%;cursor:pointer" alt="delete Icon" src="/images/icon_edit.png" width="25px" height="25px" /></a>');
				listUser.push(listItem);
			});
			viewOnDataTable(listUser);
			checkBox();
		},
		error: function(error){
			console.log(error);
			viewOnDataTable([]);
		}
	});
}

function viewOnDataTable(list){
	$('#datatable-checkbox').dataTable({
		data:list,
		destroy:true
	})
}

function checkAll(){
	ins = $('#check-all').parent().children('ins');
	ins.on('click',function(){
		$.each($('.icheckbox_flat-green'),function(i,tag){
				if(ins.parent().hasClass('checked')){
					$(tag).addClass('checked');
				}else{
					$(tag).removeClass('checked');
				}
				parent = $(tag).parent();
				parent.find('input[name=check-element]').prop('checked',$(tag).hasClass('checked'));
			});
		});
}

function viewUser(userId){
	var userDetail;
	$.each(listUsers,function(i,user){
		if(user.id===userId){
			console.log(user);
			userDetail=user;
		}
	});
	console.log(userDetail);
//	$.each(userDetail)
}

function removeUser(userId) {	
	selected = confirm("Do you want delete User have ID : " + userId);
	if(selected){
		$.ajax({
			url: '/api/users/'+ userId,
			type:'delete',
			complete: function(res){
			if(res.status===200){
				showMessage('nofitication',res.responseText,true);
			}else{
				showMessage('nofitication',res.responseText,false);
			}
			loadTable();
		}
		});
	}
}



function deleteAll(){
	$('.checkbox-delete:checkbox:checked').each(function(i,element){
		console.log($(element).val());
	})
}

function showMessage(tagId,msg,isSuccess){
	$('#'+tagId).attr('style','display:block');
    $('#'+tagId).attr('style','display:block');
    if(isSuccess){
        $('#'+tagId).prop('class','alert alert-success');
        $('#'+tagId).html('<strong>Success!!!<strong> '+msg);
        
    }else{
        $('#'+tagId).prop('class','alert alert-danger');
        $('#'+tagId).html('<strong>Fail!!!<strong> '+msg);
    }
    
    $('#'+tagId).fadeIn(50);
   $('#'+tagId).hide().slideDown(500);
   setTimeout(function() {
       $('#'+tagId).fadeOut(500);
   }, 1500);
}
$(function(){
	$('#btnAddUser').click(function(e){
	    e.preventDefault();
	    $('input').next().remove();		
		addUser();
	});
});
function addUser() {	
	var userInfo = $('#form-add-user').serializeJSON({parseBooleans: true});
	var jsonUserInfo = JSON.stringify(userInfo);

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : '/api/users',
		dataType : 'json',
		data : jsonUserInfo,
		complete : function(response) {
			if(response.status===200){
				showMessage('notification-addUser',response.responseText, true);
				loadTable();
			}else if(response.status===400 && response.responseJSON.validated===false){
				console.log(response);
                $.each(response.responseJSON.errorMessages,function(key,value){
    	            $('input[name='+key+']').after('<span style="color: red;">'+value+'</span>');
                });
			}else {
				showMessage('notification-addUser',response.responseText, false);
			}			
		}
	}); 
}

function multiDelete(){
	listId = new ListIdWrapper();
	if($('.checkbox-delete:checkbox:checked').length===0){
		alert('Ban nen chon it nhat 1 user de xoa');
		return;
	}
	$('.checkbox-delete:checkbox:checked').each(function(i,element){
		listId.addIdToArray($(element).val());
	});	
	selected = confirm('Are you really delete !!!');
	if(selected){
		$.ajax({
			url:'/api/users',
			type:'DELETE',
			contentType : 'application/json; charset=utf-8',
			data: JSON.stringify(listId),
			complete: function(res){
				if(res.status===200){
					showMessage('nofitication',res.responseText,true);
					loadTable();
				}else{
					showMessage('nofitication',res.responseText,false);
				}
			}
		});
	}
}
function checkBox(){
	$.each($('.icheckbox_flat-green'),function(i,tag){
		$(tag).on('click',function(){
			if(!$(this).hasClass('checked')){
				$(this).addClass('checked');
			}else{
				$(this).removeClass('checked');
			}
			parent = $(this).parent();
			parent.find('input[name=check-element]').prop('checked',$(this).hasClass('checked'));
		});
	});
}