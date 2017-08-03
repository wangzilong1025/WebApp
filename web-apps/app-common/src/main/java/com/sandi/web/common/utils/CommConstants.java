package com.sandi.web.common.utils;

/**
 * Created by dizl on 2015/5/8.
 */
public class CommConstants {
    public class CrudDaoSql {
        public static final String CRUD_FLAG = "$$GeneralCrud$$";
        public static final String FIND_BY_ID = "findById";//根据主键查询
        public static final String FIND_LIKE = "findLike";//根据实体类模糊查询
        public static final String FIND_LIKE_COUNT = "findLikeCount";//获取查询数量
        public static final String FIND_BY_ENTITY = "findByEntity";//根据实体查询
        public static final String FIND_BY_ENTITY_COUNT = "findByEntityCount";//获取查询数量
        public static final String FIND_NOT_THIS_ENTITY = "findNotThisEntity";//查询不是该实体的数据
        public static final String FIND_NOT_THIS_ENTITY_COUNT = "findNotThisEntityCount";//获取查询数量
        public static final String FIND_NOT_LINK_ENTITY = "findNotLikeEntity";//查询和该实体不相同的数据
        public static final String FIND_NOT_LINK_ENTITY_COUNT = "findNotLikeEntityCount";//获取查询数量
        public static final String SAVE = "save";//插入数据
        public static final String UPDATE_BY_ID = "updateById";//根据主键更新数据
        public static final String DELETE_BY_ID = "deleteById";//根据主键删除数据
        public static final String DELETE_BY_ENTITY = "deleteByEntity";//根据实体类删除数据
        public static final String INSERT_UPB_HIS = "insertUpbHis";//将数据写入到历史表中
        public static final String INSERT_DEL_HIS = "insertDelHis";//将数据写入到竣工表中
        public static final String GET_SYSDATE = "getSysDate";//获取数据库当前时间
        public static final String FIND_BY_SQL = "findBySql";//根据sql查询返回
        public static final String KEY_DAO_CLASS = "@DaoClass";
        public static final String KEY_PARAMS = "@Param";
        //edit by 肖克 sql查询增加静态变量 2016年1月22日15:30:21
        public static final String KEY_PARAM = "param";
        public static final String KEY_SQL = "sql";
        //edit end
        public static final String FOR_EACH_SAVE_FLAG = "$$forEachSave$$";
    }

    public class SplitTable {
        public static final String splitTableStartFlag = "$$";
        public static final String splitTableEndFlag = "$$";
    }

    //状态
    public class State {
        public static final String Y = "Y";//是
        public static final String N = "N";//否
        public static final String E = "E";//无效状态
        public static final String U = "U";//有效状态
        public static final String I = "I";
        public static final int STATE_NORMAL = 1;//有效状态
        public static final long STATE_NORMAL_L = 1L;//有效状态
        public static final int STATE_ABNORMAL = 0;//无效状态
        public static final long STATE_ABNORMAL_L = 0L;//无效状态
    }

    public class Config {
        public static final String MODULE_NAME = "module_name";
        public static final String IS_USE_CACHE = "is_use_cache";
        public static final String LOAD_REDIS_CACHE = "load_redis_cache";
    }

    //排序
    public class OrderType {
        public static final String ASC = "ASC";
        public static final String DESC = "DESC";
    }

    //表单配置
    public class Dync {
        public static final String BUSI_ID = "busiId";//业务编码
        public static final String OPERATE_ID = "operateId";//操作编码
        public static final String BUSI_FRAME_ID = "busiFrameId";//业务框架
        public static final String MODULE_ID = "moduleId";//数据来源1PC2APP
    }
    //工作流程
    public class Process{

        public static final int PROCESS_STATE_WAIT = 0;//流程待启用
        public static final int PROCESS_STATE_USE = 1;//流程正在使用
        public static final int PROCESS_STATE_CANCEL = 2;//流程已作废
        public static final int PROCESS_STATE_DEL = 3;//流程已删除

        public static final int PROCESS_INSTANCE_STATE_WAIT = 0;//流程状态待启动
        public static final int PROCESS_INSTANCE_STATE_NORMAL=1;//流程状态处理中
        public static final int PROCESS_INSTANCE_STATE_DONE=2;//流程状态完成处理
        public static final int PROCESS_INSTANCE_STATE_STOP=3;//流程状态已终止

        public static final int PROCESS_TYPE_WORKFLOW = 1;//日常工作流程
        public static final int PROCESS_TYPE_OTHER = 2;//其他流程

        public static final int PARAM_TYPE_STRING=0;//参数类型是字符串
        public static final int PARAM_TYPE_NUMBER=1;//参数类型是数字
        public static final int PARAM_TYPE_DATE=2;//参数类型是日期

        public static final int POINT_TYPE_START = 0;//流程开始节点Id
        public static final int POINT_TYPE_END = -1;//流程结束节点
        public static final int POINT_TYPE_TASK = 1;//任务节点
        public static final int POINT_TYPE_PROCESS = 3;//流程节点

        public static final int TASK_STATE_WAIT=0;//任务状态待处理
        public static final int TASK_STATE_DONE=1;//任务状态已处理
        public static final int TASK_STATE_CANCEL=2;//任务状态已处理
        public static final int TASK_STATE_WAIT_CHILD_PROCESS=3;//任务状态等待子流程结束
        public static final int TASK_STATE_STOP=4;//任务状态已终止
        public static final int TASK_STATE_ERROR=99;//任务状态出错

        public static final String TASK_ASSIGN_USER="0";//指定处理人
        public static final String TASK_ASSIGN_STATION="1";//指定岗位
        public static final String TASK_ASSIGN_PROCESS_CREATOR="2";//流程创建人
        public static final String TASK_ASSIGN_TASK_DEALER="3";//某一节点处理人

        public static final int CANDATE_TYPE_ROLE=0;//角色
        public static final int CANDATE_TYPE_USER=1;//人
    }

    public class ChannelId{
        public static final int ALL = 0;
        public static final int PC = 1;
        public static final int APP = 2;
    }
    public class Expression{
        public static final int EXPRESSION_TYPE_PROCESS = 1;//流程判断规则表达式
    }

    public class EventType{
        public static final int BEFORE_EVENT = 1;
        public static final int RETURN_EVENT = 2;
        public static final int TIMEOUT_EVENT = 3;
    }

    public class Mess{
        public static final String START_FLAG = "{";
        public static final String END_FLAG = "}";
        public static final String USER_ID = "USER_ID";
        public static final String BILL_ID = "BILL_ID";
        public static final String EMAIL = "EMAIL";
        public static final String SMS = "SMS";
        public static final String WEB = "WEB";
        public static final String APP = "APP";
        public static final String WEB_APP = "WEB_APP";
    }

    public class CrmInterfaces{
        public static final String ESOP_SEND_MESSAGE_CRM = "ESOP_SEND_MESSAGE_CRM";
        public static final String ESOP_GET_GROUP_INFO = "PT-SH-FS-OI4567";//多条件查询集团信息
    }

    public class CommentConstants {
        /**
         * 评论类型分组编号
         */
        public class GroupId{
            /**
             * 点赞组
             */
            public static final String CMT_GROUP_ID_1 = "1";
            /**
             * 五星评论组
             */
            public static final String CMT_GROUP_ID_2 = "2";
            /**
             * 评论组
             */
            public static final String CMT_GROUP_ID_3 = "3";
        }
    }
}
