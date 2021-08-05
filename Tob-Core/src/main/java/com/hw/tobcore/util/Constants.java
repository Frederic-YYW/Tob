package com.hw.tobcore.util;
public final class Constants {

    public final class Env {
        public static final String BASE_HOME = "base.home";
        public static final String BASE_CONF = "base.conf";
    }

    public final class ServiceProtocol {
        public static final String TCP = "tcp";
        public static final String HTTP = "http";
        public static final String MQ = "mq";
    }

    public final class UserStatus {
        public static final int NORMAL = 0;  //正常
        public static final int FORBID = 1; // 禁止
    }

    public final class UserProfileStatus {
        public static final int INIT = 0;
        public static final int NORMAL = 1;  //正常
        public static final int CONFLICT_CARD = 2; //证件不一致
        public static final int CONFLICT_IRIS = 3; //虹膜不一致
        public static final int CONFLICT_FACE = 4; //人脸不一致
        public static final int EXIST = 5;
    }

    public final class IrisStatus {
        public static final int INIT = 0;  //正常
        public static final int NORMAL = 1;  //正常
        public static final int CONFLICT_IRIS = 2; //虹膜已存在
        public static final int DELETE = 3; //虹膜已删除
    }

    public final class RoleStatus {
        public static final int SUPERADMIN = 0;  //超级管理员
        public static final int GENERALADMIN = 1;  //普通管理员
    }

    public final class OpType {
        public static final String REGISTER = "REG";  //注册
        public static final String RECOGNIZE = "RECO"; //查验
        public static final String CHECK = "CHECK";     //核验
    }

    public final class JobStatus {
        public static final int SUBMIT = 0;  //提交
        public static final int SUCCESS = 1; //成功
        public static final int FAILED = 2;  //失败
        public static final int REJECT = 3;   //拒绝
        public static final int CLOSE = 4;   //关闭
        public static final int PROCESS = 5;   //处理中
    }

    public final class JobFileType {
        public static final String ALGORITHM = "algorithm"; //算法库
        public static final String CONFIG = "config"; //配置文件
        public static final String LOG = "log"; //日志文件
        public static final String IMAGE = "image"; //异常图片
        public static final String SCENE_IMAGE = "scene_image"; //现场图片
        public static final String DESC = "desc"; //描述文件
        public static final String DB = "db"; //描述文件
        public static final String RESULT = "result";
    }

    public final class JobFileStatus {
        public static final int ENABLED = 1; //算法库
        public static final int DISABLED = 2; //配置文件
    }

    //注册，查验，核验状态
    public final class OpLogStatus {
        public static final int INIT = -1;  //初始
        public static final int RECO_FAILED = 0; //未比中
        public static final int SUCCESS = 1;  //注册成功或比中
        public static final int IRIS_EXISTS = 2;   //虹膜已存在
        public static final int CARD_FAILED = 3;   //证件信息不一样
        public static final int CARD_EXIST = 4;   //证件已存在
        public static final int FAILED = 5;   //其他错误
        public static final int IRIS_MEMORY = 6; //虹膜内存数据不一致
    }


    //注册记录同步上传状态
    public final class RegSyncStatus {
        public static final int INIT = 0;  //未上传
        public static final int SUCCESS = 1; // 上传成功
        public static final int FAILED = 2;  // 上传失败
    }

    public final class ReqStatus {
        public static final int INIT = 0;  //初始
        public static final int SUCCESS = 1; //成功
        public static final int RETRY_FAILED = 2;  //可重复提交错误
        public static final int FAILED = 3;   //不可重复提交错误
    }

    public final class User {
        public static final String COOKIE_USER = "USER";
    }

    public final class jsonView {
        public static final int CODE_SUCCESS = 200;
        public static final int CODE_FAILED = 400;

        public static final String STATUS_SUCCESS = "success";
        public static final String STATUS_FAIL = "fail";
        public static final String NONE_LOGIN = "none_login";

        public static final String ERRMSG_PAGE = "分页信息为空";
        public static final String ERRMSG_ID = "主键ID为空";
        public static final String ERRMSG_OBJ = "对象为空";
    }

    //算法库启用状态
    public final class AlgoStatus {
        public static final int DISABLE = 0;
        public static final int ENABLE = 1;
    }

    //同步目标支持
    public final class SyncTarget {
        public static final String HONGXING = "hongxing";
        public static final String SIYUE = "siyue";
        public static final String HONGBA = "hongba";
    }

    //同步启用状态
    public final class SyncStatus {
        public static final int DISABLED = 0;
        public static final int ENABLED = 1;
    }

    public final class AdminStatus {
        public static final int DISABLED = 0; //未注册
        public static final int ENABLED = 1; //已注册
    }

    //数据能否删除
    public final class ButtonStatus {
        public static final int ENABLED = 0; //可用
        public static final int DISABLED = 1; //不可用
    }


    //设备同步操作类型
    public final class SyncOpType {
        public static final int ADD = 1;
        public static final int DELETE = 2;
        public static final int UPDATE = 3;
    }

    public final class IsOnLine {
        public static final int ENABLED = 1;
        public static final int DISABLED = 0;
    }

    //设备同步操作状态
    public final class SyncOpStatus {
        public static final int INIT = 0;
        public static final int SEND = 1;
        public static final int SUCCESS = 2;
        public static final int FAILED = 3;
    }

    //
    public final class EyeFlag {
        public static final int LEFT_EYE = 1;  //左眼匹配
        public static final int RIGHT_EYE = 2; //右眼匹配
        public static final int BOTH_EYE = 3;   //双眼匹配
    }

    public final class EyeStatus {
        public static final int NORMAL = 0;      //采集正常
        public static final int UNNORMAL = 1;    //未采集
    }

    //查验记录中查验类型
    public final class RecoType {
        public static final int FACE = 1;   //脸部核验
        public static final int IRIS = 2;   //虹膜核验
        public static final int FACE_IRIS = 3; //虹膜+脸部核验
        public static final int FACE_CARD = 4; //证件+脸部核验
        public static final int FACE_CARD_IRIS = 5; //证件+脸部核验+虹膜
    }

    //认证查验比对状态
    public final class FaceCardMatchStatus {
        public static final int SUCCESS = 1;   //成功
        public static final int FAILED = 2;   //失败
    }

    //虹膜特征大小
    public final class Feature {
        public static final int IRIS_FEATURE_SIZE = 512;
        public static final int IRIS_RECO_FEATURE_SIZE = 1024;
    }

    //特征类型
    public final class FeatureType {
        public static final int REG = 1;
        public static final int RECO = 2;
    }

    //特征存储文件
    public final class FeatureFile {
        public static final String TMPFS_PROPERTY_CONF_NAME = "feature.tmpfs.enabled";
        public static final String DISK_PROPERTY_CONF_NAME = "feature.disk.enabled";

        public static final int FEATURE_TMPFS_SAVE_ENABLED = 1;
        public static final int FEATURE_TMPFS_SAVE_DISABLED = 0;

        public static final int FEATURE_DISK_SAVE_ENABLED = 1;
        public static final int FEATURE_DISK_SAVE_DISABLED = 0;

        public static final String LEFT_FEATURE_FILENAME = "leftFeature.bin";
        public static final String LEFT_FEATURE_ID_FILENAME = "leftFeatureId.bin";
        public static final String LEFT_FEATURE_ID_FILENAME_TEMP = "leftFeatureId_temp.bin";
        public static final String LEFT_FEATURE_UID_FILENAME = "leftFeatureUid.bin";
        public static final String RIGHT_FEATURE_FILENAME = "rightFeature.bin";
        public static final String RIGHT_FEATURE_ID_FILENAME = "rightFeatureId.bin";
        public static final String RIGHT_FEATURE_UID_FILENAME = "rightFeatureUid.bin";

        public static final String LEFT_FEATURE_MD5_FILENAME = "leftFeatureMd5.bin";
        public static final String RIGHT_FEATURE_MD5_FILENAME = "rightFeatureMd5.bin";
    }

    public final class Regex {

        public static final String calAttResultRegex = "^[0-2][0-9]:[0-5][0-9]$"; //统计出的考勤时间的正则 HH:mm

        public static final String calAttByDateRegex = "^[\\d]{4}-[\\d]{2}-[\\d]{2}$"; //计算考勤依据的时间 YYYY-mm-dd

        public static final String calAttByMonthRegex = "^[\\d]{4}-[\\d]{2}-[\\d]{2}$"; //计考勤依据的时间 YYYY-mm

        public static final String dateTimeRegex = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)\\s+([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$"; //计算考勤依据的时间 YYYY-mm-dd hh:mm:ss

        public static final String time = "^[0-6][0-9]$";//两位位数秒

        public static final String cardno = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" + "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";//身份证验证

        public static final String csrq = "((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(10|12|0?[13578])([-\\/\\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(11|0?[469])([-\\/\\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(0?2)([-\\/\\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([3579][26]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$))";//身份证验证
    }

    public final class SubChannel {
        public static final String delete = "_DELETE";
        public static final String modify = "_MODIFY";
        public static final String register = "_REGISTER";
        public static final String recognition = "_RECOGNITION";
        public static final String check = "_CHECK";
        public static final String extract = "_EXTRACT";
    }

    public final class RedisQueue {
        public static final String EXTRACT_QUEUE = "EXTRACT_QUEUE";
        public static final String REGISTER_QUEUE = "REGISTER_QUEUE";
        public static final String MATCH_QUEUE = "MATCH_QUEUE";
    }

    public final class RediStr {
        public static final String NODE_COUNT = "NODE_COUNT";
        public static final String NODE_MAP = "NODE_MAP";
        public static final String NODE = "NODE_";
        public static final String NODE_STATUS_MAP = "NODE_STATUS_MAP";
    }

    //特征类型
    public final class BioType {
        public static final int LEFT_IRIS = 0B01;
        public static final int RIGHT_IRIS = 0B10;
        public static final int IRIS = 0B11;
        public static final int FACE = 0B100;
    }

    //图像存储位置
    public final class SaveImgLocation {
        public static final int DB = 0;
        public static final int FILE = 1;
    }

    //默认序列化id
    public final class SerialID {
        public static final String ORGANIZATION_PARENT_ID = "1";//Organization 机构顶级id
        public static final String FILTER_FORBID_ORGANIZATION = "forbid";//Organization 下列列表过滤被禁止列表
    }

}
