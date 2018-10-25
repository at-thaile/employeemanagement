$('.msg-error').hide();
$('.button-edit').hide();
var user_profile;
var list_group = [] ;
var list_role = [] ;
var list_item= [];
function customDate(createdAt) {
	var d = new Date(createdAt);
	var getMonth = (d.getMonth() + 1) > 10 ? (d
			.getMonth() + 1)
			: ("0" + (d.getMonth() + 1));
	var getHours = (d.getHours() < 10) ? ("0" + d
			.getHours())
			: (d.getHours());
	var getMinutes = (d.getMinutes() < 10) ? ("0" + d
			.getMinutes())
			: (d.getMinutes());
	var datestring = d.getDate() + "-"
			+ getMonth + "-"
			+ d.getFullYear() + " "
			+ getHours + ":" + getMinutes;
	return datestring;
}
function viewProfile() {
	var URL = '/my-profile';
	$.ajax({
		url : URL,
		type : 'GET',
		success : function(user) {
			imageUrl ='/images/'+user.pathImage;
			console.log(imageUrl);
			sessionStorage.setItem("avatar", user.pathImage);
			$('#ava-left').prop('src',imageUrl);
			$('#ava-right').prop('src',imageUrl);
			user_profile = user;
			console.log(user_profile);
		
			sessionStorage.setItem("username", user.fullname);
			$.each($('.username-wel'),function(i,tag){
				$(tag).text(user.fullname);
			});
			$.each(user.listRole,function(i,role){
				if(role.scope==='SYSTEM'){
					if(role.roleName==='ADMIN'){
						$.each($('.menu-navv'),function(i,tag){
							$(tag).prop('style','display:block;');
						});
					}
					sessionStorage.setItem("role", role.roleName);
				}
			});
			$('.avatar-view').attr('src',imageUrl);
			createAt = customDate(user.createdAt);
			$('#form-user').find('input[name=id]').val(user.id);
			$('#form-user').find('input[name=fullname]').val(user.fullname).attr('readonly','readonly');
			$('.my-name').text(user.fullname);
			$('#form-user').find('input[name=email]').val(user.email).attr('readonly','readonly');
			$('#form-user').find('input[name=phone]').val(user.phone).attr('readonly','readonly');
			$('#form-user').find('input[name=birthday]').val(user.birthday).attr('readonly','readonly');
			$('#form-user').find('input[name=address]').val(user.address).attr('readonly','readonly');
			$('.my-address').html(' '+user.address+", TP Đà Nẵng");
			$('#form-user').find('input[name=createdAt]').val(createAt).attr('readonly','readonly');
			
			if (user.gender === true) {
				$('#genderF').parent().addClass('checked');
				$('#genderF').prop('checked',true);
			} else {
				$('#genderM').parent().addClass('checked');
				$('#genderM').prop('checked',true);
			}
			
			$.each(user.listGroup, function(i, group) {
				createAt = customDate(group.createdAt);
				list_item =[];
				i=i+1;
				list_item.push('<tr>');
				list_item.push('<td>'+i+'</td>');
				list_item.push('<td>'+group.name+'</td>');
				list_item.push('<td>'+group.description+'</td>');
				list_item.push('<td class="hidden-phone">'+createAt+'</td>');
				list_item.push('</tr>');
				
			});
			$('#my-list-group').append(list_group.toString());
			
			console.log(user.listRole);
			$.each(user.listRole, function(i, role) {
				
				createAt = customDate(role.createdAt);
				i=i+1;
				list_item =[];
				list_item.push('<tr>');
				list_item.push('<td>'+i+'</td>');
				list_item.push('<td>'+role.roleName+'</td>');
				list_item.push('<td>'+role.description+'</td>');
				list_item.push('<td class="hidden-phone">'+createAt+'</td>');
				list_item.push('<td>'+role.scope+'</td>');
				list_item.push('</tr>');
				list_role.push(list_item);
				
			});
			
			$('#my-list-role').append(list_role.toString());
		}
	});
}

function viewEditProFile() {
	// view 
	
	$('#active-edit').addClass('active');
	$('#active-edit').find('a').attr('aria-expanded','true');
	$('#tab_content3').addClass('active in');
	$('#tab_content1').removeClass('active in');
	$('#tab_content2').removeClass('active in');
	$('#form-user').find('input[name=fullname]').removeAttr('readonly');
	$('#form-user').find('input[name=phone]').removeAttr('readonly');
	$('#form-user').find('input[name=birthday]').removeAttr('readonly');
	$('#form-user').find('input[name=birthday]').attr('type', 'date');
	$('#form-user').find('input[name=birthday]').val(user_profile.birthday);
	$('#form-user').find('input[name=address]').removeAttr('readonly');
	$('#form-user').find('input[name=createdAt]').val(user_profile.createdAt);
	$('#form-user').find('input[type=reset]').fadeIn(1000);
	$('.button-edit').fadeIn(1000);
}


$(function(){
	viewProfile();

	$("#crop-avatar").hover(function(){$("#label-avata").show();},
            function(){$("#label-avata").hide();});
	$('#upload').change(function() {
		var fd = new FormData();
		fd.append('file',$(this)[0].files[0]);
		$.ajax({
			type:'put',
			url:'/my-profile/update-avatar',
			contentType: false,
			processData: false,
			data: fd,
			complete: function(res){
				console.log(res);
				if(res.status===200){
					$('.avatar-view').prop('src','/images/'+res.responseJSON.pathImage)
					$('#ava-left').prop('src','/images/'+res.responseJSON.pathImage);
					$('#ava-right').prop('src','/images/'+res.responseJSON.pathImage);
					sessionStorage.setItem("avatar", '/images/'+res.responseJSON.pathImage);
				}else if(res.status===304){
					alert('Format file wrong!!!');
				}else{
					alert('Have bug!!!');
				}
			}
		});
	})
	
	$('#doEditProFile').click(function(){
		var URL = "/my-profile";
		var proFileForm = $('#form-user').serializeJSON();
		console.log(proFileForm);
		var jsonStringProFile = JSON.stringify(proFileForm);
		console.log(jsonStringProFile);
		$.ajax({
			url : URL,
			type : 'PUT',
			contentType : "application/json; charset=utf-8",
			data : jsonStringProFile,
			dataType : 'json',
			complete : function(res) {
				if (res.status === 200 || res.status ===201){
					viewProfile();
					$('.msg-error').fadeIn(1000);
					$('.msg-error').addClass('btn-success');
					$('.msg-error').text('Update Profile Success!');
					setTimeout(function() {
						$('.msg-error').hide(1000);
					}, 8000);
					
				}
				if (res.status===400){
					$('.msg-error').fadeIn(1000);
					$('.msg-error').addClass('btn-danger');
					$('.msg-error').removeClass('btn-success');
					$('.msg-error').text(res.responseText);
					setTimeout(function() {
						$('.msg-error').fadeOut(1000);
					}, 8000);
				}
				console.log(res);
			}
		});
	});
	
	//to do change password
	$('#checkPassword').click(function() {
		var URL = "/my-profile/change-password";
		var passwordForm = $('#demo-form2').serializeJSON();
		console.log(passwordForm);
		var jsonStringPassword = JSON.stringify(passwordForm);
		console.log(jsonStringPassword);
		$.ajax({
			url : URL,
			type : 'PUT',
			contentType : "application/json; charset=utf-8",
			data : jsonStringPassword,
			dataType : 'json',
			complete : function(res) {
				if (res.status === 200 || res.status ===201){
					$('.msg-error').fadeIn(1000);
					$('.msg-error').addClass('btn-success');
					$('.msg-error').text('Update Password Success!');
					setTimeout(function() {
						$('.msg-error').fadeOut(1000);
					}, 8000);
				}
				if (res.status===409 || res.status===400){
					$('.msg-error').fadeIn(1000);
					$('.msg-error').addClass('btn-danger');
					$('.msg-error').text(res.responseText);
					setTimeout(function() {
						$('.msg-error').fadeOut(1000);
					}, 8000);
				}
			}
		});
	})
	
});

