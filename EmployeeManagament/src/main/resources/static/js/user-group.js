loadTable();
$(window).load(function(){
	if(sessionStorage.getItem("role")==='ADMIN'){
		$.each($('.menu-navv'),function(i,tag){
			$(tag).prop('style','display:block;');
		});
	}
	checkAll();
	$('#delete-all').on('click',function(){
		deleteAll();
	});
	$('#email').keyup(function(){
		$.ajax({
			type:'get',
			url:'/api/groups/'+group_id+'/users/search-not-in?searchParam='+$(this).val(),
			success: function(data){
				var html='';
				$.each(data,function(i,user){
					html+='<option value="'+user.email+'" label="'+user.fullname+'">';
				});
				$('#listUser').html(html);
			},
			error: function(res){
			}
		});
	});
	
	$('#add_user').submit(function(e){
		email = {
					"email":""
				};
		email.email = $('#email').val();
		$.ajax({
			type:'post',
			url:'/api/groups/'+group_id+'/users',
			contentType : 'application/json; charset=utf-8',
			data: JSON.stringify(email),
			complete: function(res){
				if(res.status===200){
					showMessage('alert',res.responseText,true);
					loadTable();
					$('#email').val('');
				}else{
					showMessage('alert',res.responseText,false);
				}
			}
		});
		return false;
		
		
	});
	
});

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

function loadTable(){
	var listUser=[];
	$.ajax({
		url:'/api/groups/'+group_id+'/users',
		type:'GET',
		success: function(data){
			$.each(data,function(i,user){
				listItem=[];
				listItem.push('<div class="icheckbox_flat-green" style="position: relative;">'
						+'<input type="checkbox" value="'+user.id+'" name="check-element" class="flat checkbox-delete" style="position: absolute; opacity: 0;">'
						+'<ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255); border: 0px; opacity: 0;">'
						+'</ins></div>');
				listItem.push(user.id);
				listItem.push(user.email);
				listItem.push(user.fullname);
				listItem.push(user.birthday);
				listItem.push((user.gender===true)?'Female':'Male');
				listItem.push(user.address);
				listItem.push(user.phone);
				listItem.push('<img style="margin:0% 10%;cursor:pointer" alt="delete Icon" src="/images/icon-view.png" width="25px" height="25px" onclick="viewUserInfor('+user.id+')" />'
						+'<img style="margin:0% 10%;cursor:pointer" alt="delete Icon" src="/images/icon_trash.png" width="25px" height="25px" onclick="removeUser('+user.id+')" />'
				);
				listUser.push(listItem);
			});
			showOnDataTable(listUser);
			checkBox();
		},
		error: function(error){
			console.log(error);
			showOnDataTable([]);
		}
	});
}

function showOnDataTable(list){
	$('#datatable-checkbox').dataTable({
		data:list,
		destroy:true,
		 columnDefs: [
			    { targets: [0], "orderable": false},
			  ]
	})
}

function removeUser(userId){
	selected = confirm("Are you really delete User have ID : "+userId);
	if(selected){
		$.ajax({
			url:'/api/groups/'+group_id+'/users/'+userId,
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

function showMessage(tagId,msg,isSuccess){
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
        $('#'+tagId).fadeOut(1500);
    }, 3000);
}

function viewUserInfor(id){
	$('#male').parent().show();
	$('#female').parent().show();
	$.ajax({
		url:'/api/groups/'+group_id+'/users/'+id,
		type:'GET',
		complete: function(res){
			if(res.status===200){
				console.log(res);
				$('#userPopup').modal('show');
				$('#id').val(res.responseJSON.id);
				$('#fullName').val(res.responseJSON.fullname);
				$('#emailshow').val(res.responseJSON.email);
				$('#block').val(res.responseJSON.nonLocked);
				$('#birthday').val(res.responseJSON.birthday);
				$('#createdAt').val(res.responseJSON.createdAt);
				if(res.responseJSON.gender === true){
					$('#male').parent().hide()
				}else{
					$('#female').parent().hide();
				}
			}else{
				showMessage('nofitication',res.responseText,false);
			}
		}
	});
}

function deleteAll(){
	listId = new ListIdWrapper();
	if($('.checkbox-delete:checkbox:checked').length===0){
		alert('Please select user!!!');
		return;
	}
	$('.checkbox-delete:checkbox:checked').each(function(i,element){
		listId.addIdToArray($(element).val());
	});
	selected = confirm('Are you really delete !!!');
	if(selected){
	$.ajax({
		url:'/api/groups/'+group_id+'/users',
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
