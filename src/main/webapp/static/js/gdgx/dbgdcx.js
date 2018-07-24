var glob = "";
var globName = "";
$(function () {
    function Gdgxcx(userId) {
        this.userId = userId;
        //页面数据展示 查询  督办案件
        this.gdgxcxpage_url = "/jxkh/selectGdDcdbKh.action";
        this.query_issue_by_processId = '/jxkh/getDbIssueByProcessId.action';
        this.query_dbissue_by_processId = '/jxkh/getDbIssueByProcess.action';
        //根据processid 查询 附件
        this.attachMentUrl = '/jxkh/selecAttachList.action?processId=';
        //根据processId 查询督办案件详情
        this.commentDetailUrl = '/task/listHistorydbDetailByProcessId.action?processId=';

        //普通案件
        this.ptattachMentUrl = '/gdgx/selecPtAttachList.action?processId=';
        this.ptcommentDetailUrl = '/task/listHistoryPtCommentDetailByProcessId.action?processId=';
        // 根据taskId获得开始上报人批注详情
        this.department='/task/getDbDepartment.action?taskId=';
        //获取上报人历史批注
        this.ptDepartment='/task/getDepartment.action?issueNumber=';
        this.selectGroupUrl = '/archiveOfGroup.action';
        this.boot_table_id = "boot-table-dbrw";
        this.processId = null;
        this.groupId = null; //组id
        this.groupTypeId = null; //组的类型id
        this.columns = [{
            title : '督查督办编号',
            field : 'dcdbnum',
            align : 'center',
            valign : 'middle',
            formatter : function(value, row, index) {
                return '<span class="link-col">' + row.dcdbnum + '</span>'
            }
        }, {
            title : '案件编号',
            field : 'issuenum',
            align : 'center',
            valign : 'middle',
            formatter : function(value, row, index) {
                return '<span class="link-col">' + row.issuenum + '</span>'
            }
        }, {
            title : '案件来源',
            field : 'sourceName',
            align : 'center',
            valign : 'middle'
        }, {
            title : '扣分',
            field : 'score',
            align : 'center',
            valign : 'middle',
        }, {
            title : '考核月份',
            field : 'khyf',
            align : 'center',
            valign : 'middle'
        }];
        this.myapp = new Vue({
            el: '.wrap',
            data: {
                uploadFileList: [],
                fileList: [],
                setting: {
                    check: {
                        enable: true,
                        nocheckInherit: false
                    },
                    view: {
                        dblClickExpand: true,
                        showLine: true,
                        selectedMulti: false
                    },
                    data: {
                        simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "pId",
                            rootPId: ""
                        }
                    },
                    callback: {
                        beforeClick: function (treeId, treeNode) {
                        }
                    }
                },
                zNodes: [],
                t: {},
                liucheng: [],
                loading: false,
                bianhao: {},
                ajDetail: [],
                dbajDetail: [],
                qwgDetail: [],
                commentDetail: [],
                department: [],
                speedOfProgress: [],
                ptcommentDetail:[],
                ptDepartment:[],
                timeTaken: 0
            },
            mounted: function () {
                this.$http.get(url_prefix + '/page/json/chuliliucheng.json').then(
                    function (data) {
                        this.liucheng = data.body;
                    });
                this.$http.get(url_prefix + '/page/json/function.json').then(
                    function (data) {
                        this.functionSr = data.body;
                    });
            },
            methods: {}

        });
        this.myapp2 = new Vue({
            el: '.wrap2',
            data: {
                uploadFileList: [],
                fileList: [],
                setting: {
                    check: {
                        enable: true,
                        nocheckInherit: false
                    },
                    view: {
                        dblClickExpand: true,
                        showLine: true,
                        selectedMulti: false
                    },
                    data: {
                        simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "pId",
                            rootPId: ""
                        }
                    },
                    callback: {
                        beforeClick: function (treeId, treeNode) {
                        }
                    }
                },
                zNodes: [],
                t: {},
                liucheng: [],
                loading: false,
                bianhao: {},
                ajDetail: [],
                dbajDetail: [],
                qwgDetail: [],
                commentDetail: [],
                department: [],
                speedOfProgress: [],
                ptcommentDetail:[],
                ptDepartment:[],
                timeTaken: 0
            },
            mounted: function () {
                this.$http.get(url_prefix + '/page/json/chuliliucheng.json').then(
                    function (data) {
                        this.liucheng = data.body;
                    });
                this.$http.get(url_prefix + '/page/json/function.json').then(
                    function (data) {
                        this.functionSr = data.body;
                    });
            },
            methods: {}

        });
    }

    Gdgxcx.prototype = {
        init: function () {
            this.initTree();
            this.initEvent();
            this.initDataTable();
            this.findGdgxDate();
        },
        initEvent: function () {
            var _this = this;
            $('#myModal.close').click(function () {
                $('#myModal').modal('hide');
            });
            $('#ptmyModal.close').click(function () {
                $('#ptmyModal').modal('hide');
            });
            $('#findGdgxDate').on('click', function () {
                _this.findGdgxDate();
            });
            $("#" + this.boot_table_id).on("click-cell.bs.table", function (event, column_name, value, row) {
                if (column_name == "dcdbnum") {
                   _this.showDbGdgxPage(row.processId);
                    console.log(row)
                }else if(column_name == "issuenum") {
                   _this.showGdgxPage(row.processId,row.issuenum);
                }
            });
        },
        initTree: function () {
            var _this = this;
            $.ajax({
                method: 'post',
                url: url_prefix + this.selectGroupUrl,
                data: {
                    usrId: this.userId
                },
                dataType: 'json',
                success: function (results) {
                    console.log(results);
                    var defaultData = _this.createTree(results.result, 0);
                    $('#treeview').treeview({
                        color: "#428bca",
                        expandIcon: 'glyphicon glyphicon-chevron-right',
                        collapseIcon: 'glyphicon glyphicon-chevron-down',
                        data: defaultData,
                        onNodeSelected: function (event, data) {
                            console.log(111);
                            console.log(data);
                            glob = data.attrs.groupId;
                            globName = data.attrs.groupName;
                            if (data.nodes != null) {
                                var select_node = $('#treeview').treeview('getSelected', null);
                                if (select_node[0].state.expanded) {
                                    $('#treeview').treeview('collapseNode', select_node);
                                } else {
                                    $('#treeview').treeview('expandNode', select_node);
                                }
                            }
                            if (data.nodes == null && data.attrs.groupId != null) {
                                _this.groupId = data.attrs.groupId;
                                _this.groupTypeId = data.attrs.groupType;
                                _this.findGdgxDate();
                            } else {
                                _this.groupId = null;
                            }
                        }
                    });
                    $('#treeview').treeview('collapseAll', {silent: true});
                }
            });
        },
        //树结构
        createTree: function (jsons, pid) {
            if (jsons != null) {
                var node = null;
                for (var i = 0; i < jsons.length; i++) {
                    if (jsons[i].pid == pid) {
                        var map = {
                            text: jsons[i].groupName,
                            attrs: jsons[i],
                            nodes: this.createTree(jsons, jsons[i].id)
                        };
                        if (map.nodes == null && pid == 0) {
                            map.nodes = [];
                        }
                        if (node == null) node = [];
                        node.push(map);
                    }
                }
            }
            return node;
        },
        initDataTable: function () {
            var $table = $("#" + this.boot_table_id);
            $table.bootstrapTable({
                height: ($('.content').height() - $('.funShow').height()),
                sidePagination: "server", // 分页方式：client客户端分页，server服务端分页（*）
                pagination: true,
                striped: true, // 是否显示行间隔色
                columns: this.columns,
                method: 'post',
                contentType: "application/x-www-form-urlencoded",
                dataType: 'json',
                responseHandler: function (res) {
                    return (typeof res == "string") ? $.parseJSON(res) : res;
                },
                queryParams: function (params) {
                    return {
                        rows: params.limit, // 页面大小
                        page: params.offset / params.limit + 1, // 页码
                        startDay: $('#dtp_input1').val(),
                        endDay: $('#dtp_input2').val()
                    }
                }
            });

        },
        findGdgxDate: function () {
            var _this = this;
            // console.log(_this.groupId);

            $("#" + this.boot_table_id).bootstrapTable('refreshOptions', {
                columns: this.columns,
                url: url_prefix + this.gdgxcxpage_url,
                responseHandler: function (res) {
                    return JSON.parse(res);
                },
                queryParams: function (params) {
                    return {
                        rows: params.limit, // 页面大小
                        page: params.offset / params.limit + 1, // 页码
                        groupId: _this.groupId,
                        groupTypeId: _this.groupTypeId,
                        caseType: $("#caseType").val(),
                        startDay: $('#dtp_input1').val(),
                        endDay: $('#dtp_input2').val(),

                    }
                }
            });

        },
        showDbGdgxPage: function (processId_) {
            var _this = this;
            //清理数据
            $('.attachment-list').children().remove();
            this.processId = processId_;
            // jsp 映射表  key+用户所在角色id_即的type_，如委办局的key为key5，法制办和的key是key4，不是4或5的没有内嵌页面
            $.ajax({
                method: 'post',
                url: url_prefix + this.query_dbissue_by_processId,
                dataType: 'json',
                data: {
                    processId: processId_
                },
                success: function (data) {
                    console.log(data)
                    // 填充案件详情数据 如 ： issueObj.tMainIssueBean.issueSubject
                    data = (typeof data == "object") ? data : $.parseJSON(data);
                    _this.myapp.dbajDetail = data.tissueDcdbKhBean;
                    dcdbnum = _this.myapp.dbajDetail.dcdbnum;

                    $("#myModal").modal({backdrop: 'static'}).show();

                }
            });

            $.ajax({
                method: 'get',
                url: url_prefix + this.commentDetailUrl + this.processId,
                dataType: 'json',
                success: function (data) {
                    //初始化提示框
                    data = (typeof data == "object") ? data : $.parseJSON(data)
                    _this.myapp.commentDetail = data.rows;
                    $('[data-toggle="tooltip"]').tooltip("destroy");
                    $('[data-toggle="tooltip"]').tooltip();
                }
            });
            //案件上报时的流程
            $.ajax({
                method : 'get',
                url : url_prefix+this.department+this.processId,
                dataType : 'json',
                success : function(data) {
                    data = (typeof data=="object")?data:$.parseJSON(data)
                    _this.myapp.department=data.rows;
                    //初始化提示框
                    $('[data-toggle="tooltip"]').tooltip();
                }
            });

            $.ajax({
                method: 'get',
                url: url_prefix + this.attachMentUrl + this.processId,
                dataType: 'json',
                success: function (data) {

                    console.log(data);
                    if (data && data.length > 0) {
                        $.each(data, (i, o) => {
                            var $tr = $('<tr></tr>');
                            var $td = $('<td class="col-sm-3">' + o.USERNAME_ + '</td>');
                            var typeName = '';
                            switch (o.type) {
                                case 1:
                                    typeName = '处理前';
                                    break;
                                case 2:
                                    typeName = '处理中';
                                    break;
                                case 3:
                                    typeName = '评价';
                                    break;
                                case 4:
                                    typeName = '立案';
                                    break;
                                case 5:
                                    typeName = '处理后';
                                    break;
                                default:
                                    break;
                            }
                            var $td2 = $('<td class="col-sm-3">' + typeName + '</td>');
                            var $td3 = $('<td class="col-sm-3 link-col" ">' + o.name + '</td>');
                            $td3.on('click', (e) => {
                                showAttachment(o.save_path);
                            });
                            $tr.append($td).append($td2).append($td3);
                            $tr.appendTo($('.attachment-list'));

                        });
                    }
                }
            });
        },
        //普通案件详情
        showGdgxPage: function (processId_,issueNumber) {
            var _this = this;
            this.issueNumber=issueNumber;
            //清理数据
            $('.attachment-list').children().remove();
            this.processId = processId_;
            // jsp 映射表  key+用户所在角色id_即的type_，如委办局的key为key5，法制办和的key是key4，不是4或5的没有内嵌页面
            $.ajax({
                method: 'post',
                url: url_prefix + this.query_issue_by_processId,
                dataType: 'json',
                data: {
                    processId: processId_
                },
                success: function (data) {

                    console.log(data)
                    // 填充案件详情数据 如 ： issueObj.tMainIssueBean.issueSubject
                    data = (typeof data == "object") ? data : $.parseJSON(data);
                    _this.myapp2.ajDetail = data.tMainIssueBean;
                    console.log(_this.myapp2.ajDetail)
                    //dcdbnum = _this.myapp.ajDetail.issuenum;
                    $("#ptmyModal").modal({backdrop: 'static'}).show();



                }
            });
            $.ajax({
                method: 'get',
                url: url_prefix + this.ptcommentDetailUrl + this.processId,
                dataType: 'json',
                success: function (data) {
                    console.log(data)
                    //初始化提示框
                    data = (typeof data == "object") ? data : $.parseJSON(data)
                    _this.myapp2.ptcommentDetail = data.rows;
                    console.log(_this.myapp2.ptcommentDetail)
                    $('[data-toggle="tooltip"]').tooltip("destroy");
                    $('[data-toggle="tooltip"]').tooltip();
                }
            });
            $.ajax({
                method: 'get',
                url: url_prefix + this.ptDepartment + this.issueNumber,
                dataType: 'json',
                success: function (data) {
                    console.log(data)
                    //初始化提示框
                    data = (typeof data == "object") ? data : $.parseJSON(data)
                    _this.myapp2.ptDepartment = data.rows;
                    /*  console.log(myapp.ptDepartment)*/
                    $('[data-toggle="tooltip"]').tooltip("destroy");
                    $('[data-toggle="tooltip"]').tooltip();
                }
            });
            $.ajax({
                method: 'get',
                url: url_prefix + this.ptattachMentUrl + this.processId,
                dataType: 'json',
                success: function (data) {

                    console.log(data);
                    if (data && data.length > 0) {
                        $.each(data, (i, o) => {
                            var $tr = $('<tr></tr>');
                            var $td = $('<td class="col-sm-3">' + o.USERNAME_ + '</td>');
                            var typeName = '';
                            switch (o.type) {
                                case 1:
                                    typeName = '处理前';
                                    break;
                                case 2:
                                    typeName = '处理中';
                                    break;
                                case 3:
                                    typeName = '评价';
                                    break;
                                case 4:
                                    typeName = '立案';
                                    break;
                                case 5:
                                    typeName = '处理后';
                                    break;
                                default:
                                    break;
                            }
                            var $td2 = $('<td class="col-sm-3">' + typeName + '</td>');
                            var $td3 = $('<td class="col-sm-3 link-col" ">' + o.name + '</td>');
                            $td3.on('click', (e) => {
                                showAttachment(o.save_path);
                            });
                            $tr.append($td).append($td2).append($td3);
                            $tr.appendTo($('.attachment-list'));

                        });
                    }
                }
            });
        }
    }
    var gdgxcx = new Gdgxcx(userId);
    gdgxcx.init();
});