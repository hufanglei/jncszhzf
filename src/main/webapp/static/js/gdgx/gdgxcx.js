var glob = "";
var globName = "";
$(function () {

    // function initDataTable() {
    //     var $table = $("#" + this.boot_table_id);
    //     $table.bootstrapTable({
    //         height: ($('.content').height() - $('.funShow').height()),
    //         sidePagination: "server", // 分页方式：client客户端分页，server服务端分页（*）
    //         pagination: true,
    //         striped: true, // 是否显示行间隔色
    //         columns: this.columns,
    //         method: 'post',
    //         contentType: "application/x-www-form-urlencoded",
    //         dataType: 'json',
    //         responseHandler: function (res) {
    //             return (typeof res == "string") ? $.parseJSON(res) : res;
    //         },
    //         url: url_prefix + this.gdgxcx_page_url,
    //         queryParams: function (params) {
    //             return {
    //                 rows: params.limit, // 页面大小
    //                 page: params.offset / params.limit + 1, // 页码
    //                 startDay: $('#dtp_input1').val(),
    //                 endDay: $('#dtp_input2').val()
    //             }
    //         }
    //     });
    //
    //
    // };
    //
    // //页面初始化
    // initDataTable();

    // initDialog();


    function Gdgxcx(userId) {
        this.userId = userId;
        //this.gdgxcx_page_url = "/gdgx/gdgxPage.action";
        this.gdgxcxpage_url = "/gdgx/selectGdgxge.action";
        // 根据taskId 查询案件信息
        this.query_issue_by_processId = '/issues/getIssueByProcessId.action';
        // 根据taskId获得点击的批注详情
        this.commentDetailUrl = '/task/listHistoryCommentDetailByProcessId.action?processId=';
        //根据processid 查询 附件
        this.attachMentUrl = '/gdgx/selecAttachList.action?processId=';
        //根据taskId获取流程图
        this.speedOfProgressUrl = '/task/listActionByProcessId.action?processId=';
        //获取上报人历史批注
        this.ptDepartment='/task/getDepartment.action?issueNumber=';
        this.selectGroupUrl = '/archiveOfGroup.action';
        this.boot_table_id = "boot-table-dbrw";
        this.processId = null;
        this.groupId = null; //组id
        this.groupTypeId = null; //组的类型id
        this.columns = [{
            title: '案件编号',
            field: 'issueNumber',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                return '<span class="link-col">' + value + '</span>'
            }
        }, {
            title: '案件主题',
            field: 'issueSubject',
            align: 'center',
            valign: 'middle'
        }, {
            title: '创建时间',
            field: 'addTime',
            align: 'center',
            valign: 'middle'
        }, {
            title: '结束日期',
            field: 'endTime',
            align: 'center',
            valign: 'middle'
        }, {
            title: '紧急程度',
            field: 'emrgencyLevel_ms',
            align: 'center',
            valign: 'middle'
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
                qwgDetail: [],
                commentDetail: [],
                ptDepartment: [],
                speedOfProgress: [],
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

        },
        initEvent: function () {
            var _this = this;
            $('#myModal.close').click(function () {
                $('#myModal').modal('hide');
            });
            $('#findGdgxDate').on('click', function () {
                _this.findGdgxDate();
            });
            $("#" + this.boot_table_id).on("click-cell.bs.table", function (event, column_name, value, row) {
                if (column_name == "issueNumber") {
                    _this.showGdgxPage(row.processId,row.issueNumber);
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
                                $('#boot-table-dbrw').bootstrapTable('destroy');
                                _this.initDataTable();
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
                // url: url_prefix + this.gdgxcx_page_url,
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
            // if (this.groupId == null) {
            //     layerAlert("请选择组织机构!");
            //     return;
            // }
            var _this = this;
            // console.log(_this.groupId);

            //获取案件归口id
            var issueSubject = function () {
                var issueSubjectId = $("#issueSubject").val();
                if ("0" != issueSubjectId && issueSubjectId) {
                    return issueSubjectId;
                }
                issueSubjectId = $("#xiang").val();
                if ("0" != issueSubjectId && issueSubjectId) {
                    return issueSubjectId;
                }
                issueSubjectId = $("#lei").val();
                if ("0" != issueSubjectId && issueSubjectId) {
                    return issueSubjectId;
                }
                return "";
            };

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
                        groupId: _this.groupId ,
                        groupTypeId: _this.groupTypeId ,
                        type:2,
                        caseType: $("#caseType").val(),
                        startDay: $('#dtp_input1').val(),
                        endDay: $('#dtp_input2').val(),
                        issueSubject: issueSubject()
                    }
                }
            });

        },
        showGdgxPage: function (processId_,issueNumber_) {
            var _this = this;
            issueNumber=issueNumber_
            //清理数据
            $('.attachment-list').children().remove();
            this.processId = processId_;
            // jsp 映射表  key+用户所在角色id_即的type_，如委办局的key为key5，法制办和的key是key4，不是4或5的没有内嵌页面
            var jsp_include_mapper = {
                /*'key4': "fzbjdjc", //法制办 页面 监督检查
                "key5": 'wbjxcjz'  //委办局页面 巡查机制*/
            };
            $.ajax({
                method: 'post',
                url: url_prefix + this.query_issue_by_processId,
                dataType: 'json',
                data: {
                    processId: processId_
                },
                success: function (data) {
                    // 填充案件详情数据 如 ： issueObj.tMainIssueBean.issueSubject
                    data = (typeof data == "object") ? data : $.parseJSON(data);
                    _this.myapp.ajDetail = data.tMainIssueBean;
                    _this.myapp.ajstatus = _this.myapp.ajDetail.status;
                    issuenum = _this.myapp.ajDetail.issueNumber;

                    $("#myModal").modal({backdrop: 'static'}).show();
                    //user_group_type全局值在jsp页面中有
                    /*  if (user_group_type == '4' || user_group_type == '5') {
                          var status = "key" + user_group_type;
                          //第一种写法
                          //var page_suffix = eval("jsp_include_mapper." + status);
                          //第二种写法
                          page_suffix = jsp_include_mapper[status];
                          $('#dbrw-cl').load(url_prefix + '/page/sub/' + page_suffix + '.jsp');
                      }*/

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
            $.ajax({
                method: 'get',
                url: url_prefix +this.ptDepartment + issueNumber,
                dataType: 'json',
                success: function (data) {
                    //初始化提示框
                    data = (typeof data == "object") ? data : $.parseJSON(data)
                    _this.myapp.ptDepartment = data.rows;
                    $('[data-toggle="tooltip"]').tooltip("destroy");
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
            _this.myapp.$http.get(url_prefix + this.speedOfProgressUrl + this.processId).then(
                function (response) {
                    var data = (typeof response.data == "object") ? response.data : $.parseJSON(response.data)
                    _this.myapp.speedOfProgress = data.rows;
                    var startTime = new Date(_this.myapp.speedOfProgress[0].startTime);
                    var endTime;
                    if (_this.myapp.speedOfProgress[_this.myapp.speedOfProgress.length - 1].endTime) {
                        endTime = new Date(_this.myapp.speedOfProgress[_this.myapp.speedOfProgress.length - 1].endTime);
                    }
                    else endTime = new Date();
                    _this.myapp.timeTaken = endTime - startTime;
                    Vue.nextTick(function () {
                        $('.dec').each(function (index, dom) {
                            var shart = setInterval(function () {
                                if ($('.shuju').height() > 0) {
                                    $(dom).height($(dom).parent().parent().innerHeight() - $(dom).parent().children('.text').innerHeight() + 'px');
                                    $(dom).css('bottom', '-' + $(dom).css('height'));
                                    clearInterval(shart);
                                }
                            }, 50)
                        })
                    })
                });
        }
    }
    var gdgxcx = new Gdgxcx(userId);
    gdgxcx.init();
});