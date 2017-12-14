package com.kj.util;

public class HttpUrl {
    //判断是否需要更新APP
    public static final String IsUpdate="ver/get";
	//登录
	public static final String User_Login="login/get";
	//根据主键cardnum查询个人基本信息
	public static final String GetUserInfo="grjbxx/get";
	//个人基本信息修改
	public static final String EditGrjbxx="grjbxx/edit";

    public static final String SaveGrjbxx="grjbxx/add";

	///////////////////////////////////////////////////////////////////////////////

	//查询第一次产前检查记录
	public static final String GetYcf1="ycf/dycsf/get";
	public static final String SaveYcf1="ycf/dycsf/add";
    public static final String EditYcf1="ycf/dycsf/edit";
	public static final String GetYcf1Grjbxx="ycf/dycsf/getGrjbxx";
	public static final String GetYcf1List="ycf/dycsf/pagelst";

	//获取2-5随访记录
	public static final String GetYcf25="ycf/ewccqsf/get";
	public static final String SavesYcf25="ycf/ewccqsf/add";
	public static final String EditYcf25="ycf/ewccqsf/edit";
    public static final String GetYcfEwcGrjbxx="ycf/ewccqsf/getGrjbxx";
	public static final String GetYcf25List="ycf/ewccqsf/pagelst";

	//获取产后访视记录
	public static final String GetYcfChfsjl="ycf/chsf/get";
	public static final String SaveYcfChfsjl="ycf/chsf/edit";
	public static final String AddYcfChfsjl="ycf/chsf/add";
	public static final String GetYcfChfsjlGrjbxx="ycf/chsf/getGrjbxx";
	public static final String GetChfsjlList="ycf/chsf/pagelst";
	public static final String EditChsfjl="ycf/chsf/edit";
	//获取孕产妇42天健康检查记录
	public static  final String GetYcf42="ycf/chsf42/get";
	public static  final String SaveYcf42="ycf/chsf42/add";
	public static  final String EditYcf42="ycf/chsf42/edit";
	public static final String GetYcf42Grjbxx="ycf/chsf42/getGrjbxx";
	public static  final String GetYcf42List="ycf/chsf42/pagelst";
	///////////////////////////////////////////////////////////////////////////////
	//获取我的医疗团队
	public static final String GetMyTeam="medicalteam/pagelst";
	public static final String GetAllTeam="medicalteam/getAll";
	public static final String EditTeam="medicalteam/edit";
	public static final String AddTeam="medicalteam/add";
	//获取医疗团队人员信息
	public static final String GetTeamMember="teammember/getList";
	public static final String GetAllTeamMember="teammember/getAll";
	public static final String AddTeamMember="teammember/add";
	public static final String GetDoctor="doctor/getList";
	public static final String DelTeamMember="teammember/delete";


	///////////////////////////////////////////////////////////////////////////////////
	//列表查询服务包
	public static final String GetServicePackage="servicepack/pagelst";//（需要获取全部）
	public static final String GetAllServicePackage="servicepack/getAll";//（需要获取全部）
	//获取服务包列表
	public static final String GetSerVice="servicepack/pagelst";
	//获取服务包子内容
	public static final String GetSerViceItem="servicepackitem/getList";
	//////////////////////////////////////////////////////////////////////////////////
	//列表查询个人基本信息用户
	public static final String GetJbxxUserList="grjbxx/pagelst";
	//列表查询签约的居民信息
	public static final String GetSignedUser="signup/pagelst";
    ///////////////////////////////////////////////////////////////////////////////////////////新生儿
    public static final String GetXseGetGrjbxx = "xse/jtfs/getGrjbxx";//新生儿获取基本信息
    public static final String SaveXseSfjl = "xse/jtfs/add";//保存新生儿随访记录
    public static final String EditXseSfjl = "xse/jtfs/edit";
    public static final String GetXseList = "xse/jtfs/pagelst";

    ////////////////////////////////////////////////////////////////////////////////////////////1-8个月婴儿

	//获取精神病访视记录
	public static final String GetJsbfsjl="jsb/sf/get";
	public static final String SaveJsbfsjl="jsb/sf/edit";
	public static final String AddJsbfsjl="jsb/sf/add";
	public static final String GetJsbfsjlGrjbxx="jsb/sf/getGrjbxx";
	public static final String GetJsbfsjlList="jsb/sf/pagelst";

	//获取高血压访视记录
	public static final String GetGxyfsjl="gxy/sf/get";
	public static final String EditGxyfsjl="gxy/sf/edit";
	public static final String AddGxyfsjl="gxy/sf/add";
	public static final String GetGxyfsjlGrjbxx="gxy/sf/getGrjbxx";
	public static final String GetGxyfsjlList="gxy/sf/pagelst";

	//获取高血压访视记录
	public static final String GetTnbfsjl="tnb/get";
	public static final String SaveTnbfsjl="tnb/edit";
	public static final String AddTnbfsjl="tnb/add";
	public static final String GetTnbfsjlGrjbxx="tnb/getGrjbxx";
	public static final String GetTnbfsjlList="tnb/pagelst";

	//获取精神病信息补充表
	public static final String GetJsbXxbc="jsb/xxbc/get";
	public static final String EditJsbXxbc="jsb/xxbc/edit";
	public static final String AddJsbXxbc="jsb/xxbc/add";
	public static final String GetJsbXxbcGrjbxx="jsb/xxbc/getGrjbxx";
	public static final String GetJsbXxbcList="jsb/xxbc/pagelst";


    public static final String GetYEGrjbxx = "xse/etjkjc1/getGrjbxx";//新生儿获取基本信息
    public static final String SaveYESfjl = "xse/etjkjc1/add";//保存新生儿随访记录
    public static final String EditYESfjl = "xse/etjkjc1/edit";
    ////////////////////////////////////////////////////////////////////////////////////////////////12-30个月
    public static final String GetYE12Grjbxx = "xse/etjkjc12/getGrjbxx";//新生儿获取基本信息
    public static final String SaveYE12Sfjl = "xse/etjkjc12/add";//保存新生儿随访记录
    public static final String EditYE12Sfjl = "xse/etjkjc12/edit";
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////3-6岁
    public static final String GetYE36Grjbxx = "xse/etjkjc36/getGrjbxx";//新生儿获取基本信息
    public static final String SaveYE36Sfjl = "xse/etjkjc36/add";//保存新生儿随访记录
    public static final String EditYE36Sfjl = "xse/etjkjc36/edit";


	//老年人生活自理能力评估
	public static final String GetLnrGrjbxx="lnr/zlnlpg/getGrjbxx";
	public static final String SaveLnr="lnr/zlnlpg/add";
	public static final String EditLnr="lnr/zlnlpg/edit";
	public static final String GetLnrList="lnr/zlnlpg/pagelst";

	//签约
	public static final String SignAdd="signup/add";

	//获取精神病补充信息
	public static final String GetJsbBcxx="jsb/xxbc/get";
	public static final String SaveJsbBcxx="jsb/xxbc/edit";
	public static final String AddJsbBcxx="jsb/xxbc/add";
	public static final String GetJsbBcxxGrjbxx="jsb/xxbc/getGrjbxx";
	public static final String GetJsbBcxxList="jsb/xxbc/pagelst";

}
