<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<div style='margin-top: 25px;'>
	<!-- Nav tabs -->
	<ul class="nav nav-tabs" role="tablist">
		<li role="presentation" class="active"><a href="#paiqian"
			aria-controls="home" role="tab" data-toggle="tab">派遣</a></li>
		<li role="presentation"><a href="#paifa" aria-controls="profile"
			role="tab" data-toggle="tab">派发</a></li>
		<li role="presentation"><a href="#lianban"
			aria-controls="profile" role="tab" data-toggle="tab">联办</a></li>
		<li role="presentation"><a href="#bohui" aria-controls="profile"
			role="tab" data-toggle="tab">驳回</a></li>
		<li role="presentation"><a href="#yinananjian"
			aria-controls="profile" role="tab" data-toggle="tab">疑难案件处理</a></li>
	</ul>

	<!-- Tab panes -->
	<div class="tab-content" style='background: #fff;'>
		<div role="tabpanel" class="tab-pane active" id="paiqian">
			<div class="col clearfix">
				<div class="form-group col-sm-6">
					<div class="biaoti">选择街道</div>
					<select name="" id="" class='form-control'>
						<option value="">请选择</option>
						<option value="" v-for='item in functionSr.paiqian'>{{item}}</option>
					</select>
				</div>
			</div>
			<div class="clearfix">
				<div class="form-group col-sm-8">
					<div class="biaoti"><span style="color: red"> * </span>办理意见：</div>
					<textarea class="form-control" rows="3" v-model='paiqian_area'></textarea>
				</div>
			</div>
			<div class="clearfix"  align="center" style="margin-top: 20px">
				<div class="form-group col-sm-12">
					<button class='btn btn-success' tabType='tab1'>提交</button>
				</div>
			</div>
		</div>
		<div role="tabpanel" class="tab-pane" id="paifa">
			<div class="clearfix">
				<div class="form-group col-sm-8">
					<div class="biaoti">aaa发：</div>
					<textarea class="form-control" rows="3" v-model='paifa_area'></textarea>
				</div>
			</div>
			<div class="clearfix"  align="center" style="margin-top: 20px">
				<div class="form-group col-sm-12">
					<button class='btn btn-success' tabType='tab2'>提交</button>
				</div>
			</div>
		</div>
		<div role="tabpanel" class="tab-pane" id="lianban">
			<div class="col clearfix">
				<div class="form-group col-sm-6">
					<div class="biaoti">选择委办局</div>
					<select name="" id="" class='form-control'>
						<option value="">请选择</option>
						<option value="" v-for='item in functionSr.lianban'>{{item}}</option>
					</select>
				</div>
			</div>
			<div class="clearfix">
				<div class="form-group col-sm-8">
					<div class="biaoti"><span style="color: red"> * </span>办理意见：</div>
					<textarea class="form-control" rows="3" v-model='lianban_area'></textarea>
				</div>
			</div>
			<div class="clearfix"  align="center" style="margin-top: 20px">
				<div class="form-group col-sm-12">
					<button class='btn btn-success' tabType='tab3'>提交</button>
				</div>
			</div>
		</div>
		<div role="tabpanel" class="tab-pane" id="bohui">
			<div class="clearfix">
				<div class="form-group col-sm-8">
					<div class="biaoti"><span style="color: red"> * </span>办理意见：</div>
					<textarea class="form-control" rows="3" v-model='bohui_area'></textarea>
				</div>
			</div>
			<div class="clearfix"  align="center" style="margin-top: 20px">
				<div class="form-group col-sm-12">
					<button class='btn btn-success' tabType='tab4'>提交</button>
				</div>
			</div>
		</div>
		<div role="tabpanel" class="tab-pane" id="yinananjian">
			<div class="clearfix">
				<div class="form-group col-sm-8">
					<div class="biaoti"><span style="color: red"> * </span>办理意见：</div>
					<textarea v-model='ynaj_area' class="form-control" rows="3"></textarea>
				</div>
			</div>
			<div class="clearfix"  align="center" style="margin-top: 20px">
				<div class="form-group col-sm-12">
					<button class='btn btn-success' tabType='tab5'>提交</button>
				</div>
			</div>
		</div>
	</div>
</div>
</html>