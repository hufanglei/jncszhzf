tab_animate();
$(".taskId").val(taskId);
$(".tabpanel .btn").attr("type", "button");
var my_modal = new Vue({
	el : '.tab-content',
	data : {
		streetArr : [],
		streetSelect : "",
		area1 : '',
		area2 : '',
		area3 : '',
		area4 : '',
		area5 : '',
		streetArr : [],
		weibanjuArr : [],
		wbjSelect : "",
		streetSelect : "",
		shequSelect : '',
		wanggeSelect : '',
		neisheSelect : '',
		whetherJS : '1',
		whetherMY : '1',
		input1 : '2017-9-18',
		input2 : "2017-10-8",
		weibanju : [ '住房建设局', '建工局', '劳动保障局', '环境保护局', '安全生产局', '林业局', '农业局',
				'水务局', '文化局', '城市管理局', '交通运输局', '市场监管局', '商务局' ],
		wbjSelect : '住房建设局'
	},
	mounted : function() {
		this.$http.get(url_prefix + '/qhzd/selectQhzd.do?&qhjb=2').then(
				function(response) {
					var streetArr = JSON.parse(response.data);
					var streetArr = streetArr.rows;
					this.streetArr = streetArr;
					this.streetSelect = this.streetArr[0].qhmc;
					console.log(this.streetArr);
				});
	}
})