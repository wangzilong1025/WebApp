package com.sandi.web.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by 王子龙 on 2017-06-07.
 */
@Controller
@RequestMapping("/base")
public class BaseController {
    private static final Logger log = Logger.getLogger(BaseController.class);
    long timeToken = System.currentTimeMillis();

    @RequestMapping("/baseDetail")
    public String baseDetail(ModelMap modelMap, @RequestParam("baseAddress") String baseAddress){
        log.info(timeToken+"进入baseDetail方法!");
        try{
            log.info(timeToken+"进入baseDetail的try方法,接收参数:"+baseAddress);
            //基地动态
            String baseDynamics = null;
            //工作成效
            String workEffectiveness = null;
            //技术需求
            String  technicalRequirement = null;
            if(baseAddress.equals("厦门")){
                baseDynamics = "厦门示范基地网络平台——“科易网”（www.1633.com）经过九年的探索创新、实践积累，产品矩阵已然成型，可提供完善的城市科技创新和企业技术创新服务体系。";
                workEffectiveness = "1、针对企业在科技创新过程中“想投资找不到好项目、有难题找不到好专家”的难题，科易网持续整合各类科技服务资源，目前已有可交易科技成果25万条、技术专家10万多名、服务机构2万多家、合作院校1000多所。\n" +
                        "科易网依托pc端与移动端双平台结合，打通了技术转移全流程服务绿色通道及便捷的科技社交环境，并持续开展在线展会，截止目前，累计举办280场，参与人数263万余人，参展企业8738家，实现技术对接2.2万次，达成意向4780次。\n" +
                        "继在线展会、技术评估、专利交易等企业服务产品之后，2016年科易网新推出“老师傅”（咨询专家平台），“难题帮”（研发对接平台）、“成果帮”（对接项目资本），以及“科易视讯”等系列创新服务产品，使科技服务更贴近企业实际需求，并通过“科技服务业众创空间”不断创新、孵化新的服务品种。\n" +
                        "目前厦门示范基地已服务厦门6000多家企业，提供咨询服务5000多次，厦门市思明区和海沧区实现在线技术交易额5000多万元；截止目前，厦门示范基地首创的技术交易服务系统——“科易宝”交易金额成功突破3.6亿元！";
                technicalRequirement = "2、针对城市科技创新体系建设中“重建设轻运营、重物理轻虚拟、重数量轻质量”的问题，厦门示范基地提出“互联网+科技成果转化”服务新模式，构建有效果、可持续的“区域技术市场”。\n" +
                        "科易技术市场布局全国，并通过科易云平台，实现了各平台之间的互联互通，资源实时共享，独立而不孤立。目前科易技术市场已服务于黑龙江省、青海省、四川省、西安、厦门思明、海沧、南京高淳、江苏泰州、深圳龙岗、浙江海宁等27个省市，致力将厦门打造为全国网上技术交易中心城市。";
            }else if(baseAddress.equals("济南")){
                baseDynamics = "      2010年10月23日，“国家科技成果转化服务（济南）示范基地暨综合信息服务平台”项目在北京召开专家评审会，并顺利通过专家评审。\n" +
                        "　　会议由济南市科技局郑应德副局长主持，国家科技奖励工作办公室张木副主任、成果处姚昆仑处长、中国化工信息中心傅旭主任、山东华艺集团副总裁薛毅力、国家科技成果网负责人吴昌权、其他项目承担单位的人员及科技部发展计划司原巡视员申茂向等科技成果推广转化方面的资深专家参加了会议。";
                workEffectiveness = "      张木副主任首先介绍了国家科技奖励工作办公室提出建设“国家科技成果转化示范基地”的背景条件，郑应德副局长介绍了济南市的经济发展状况及科技创新进展。根据国家科技奖励工作办公室与济南市科技局共同建设“国家科技成果转化服务（济南）示范基地”的要求，由山东华艺集团有限公司和国家科技成果网（国科网）共建国家科技成果转化（济南）综合信息服务平台。国家科技成果转化（济南）综合信息服务平台作为国家科技奖励办与济南市政府全面合作的载体，将引进全国优势技术项目、机构、人才资源，为国家科技成果转化（济南）示范基地建设发挥基础性支撑作用。";
                technicalRequirement = "      评审会上，与会专家对“国家科技成果转化服务（济南）示范基地暨综合信息服务平台”重大科技项目进行了论证。与会专家审阅了项目可行性报告及相关资料，听取了项目建设单位的汇报，经过质询和讨论，认为：该项目符合《国家中长期科技发展规划纲要》和国家技术转移促进行动的要求，对促进国家创新试点城市建设和济南经济发展具有重要意义；济南市具备建设项目的良好政策环境和条件；该项目充分发挥国家科技成果库的成果、机构、人才等资源优势，搭建太阳能及其他新能源领域的专业化技术创新综合信息服务平台，有效促进区域新能源产业的发展；信息服务平台建设方案可行，项目承担单位具有从事科技成果管理和技术转移服务的能力和较丰富的经验、具备项目实施的组织条件，专家组一致同意项目实施方案，建议立项。（国科网 吴哲编辑报道）";
            }else if(baseAddress.equals("南宁")){
                baseDynamics = "      1.利用平台优势资源，重点培育国家重大科技成果转化示范企业，为企业提供专业优质服务，传播标杆式影响力。\n" +
                        "通过示范基地各类服务平台，提供技术合作、科研项目合作撮合、科技项目申报、市场推广指导等一条龙科技服务，重点培育科技成果转化示范企业，通过培育成果转化示范企业，鼓励和带动其他企业引进、吸收和再创新优秀的国家科技成果，促进成果转化产业化事业化。如：广西恒煊生物科技有限公司，示范基地借助示范基地综合信息服务平台，牵引公司引进和转化中国科学院曾毅院士的专利技术——鼻咽癌早期试剂，并协助公司向科技部门申请设立了广西第一批院士工作站。曾毅院士发明的鼻咽癌早诊检测法被公认为国内外最简易、最快速、成本最低、检出率最高，已获得国家科技部及卫生部科技成果认证、推荐和推广，并获得国内外多项科技奖项，孵化基地看准该项科技成果巨大社会效益和市场潜力，鼓励和支持恒煊公司进行落地转化，并在转化服务过程中，继续给予创业导师、法律顾问等指导服务，帮助其推广产品、开拓市场。";
                workEffectiveness = "      2、搭建国家科技成果交易大厅，增添成果转化新途径\n" +
                        "在国家科技成果转化服务（南宁）综合信息服务平台、中国—东盟（南宁）科技信息网等平台基础上，创建集技术交易、知识产权服务、技术合同认定登记、成果展示、查新、发布等多功能一体的科技成果（产权）交易服务平台——国家科技成果交易服务大厅。交易大厅总面积占280平方米，设立LED成果展示区、技术洽谈区及服务窗口三大功能区。其中LED成果展示区主要展示最新的科技成果和技术需求；技术洽谈区主要针对申请推广的技术、需解决的技术难题，专人为供需双方提供撮合；服务窗口分设科技成果交易窗口、技术合同认定登记窗口，科技成果交易窗口提供科技成果交易服务、科技成果查阅服务、成果展示申请登记服务、成果需求登记服务等，技术合同认定登记窗口为客户提供技术合同认定登记及咨询等服务。2014年8月启用以来，LED成果展示区展示国家可以成果200多项，技术洽谈区为100多家企业提供交流场所，服务窗口为企业提供信息和咨询服务1000多项等。\n" +
                        "3、开拓东盟技术转移市场\n" +
                        "协助政府科技部门组织开展东盟技术交流、科技展览等活动，组织南宁市在生物农业、生物工程、机械制造、节能环保等领域有优势的企业参加，进行科技推广合作，并通过国际合作专家顾问与当地科技政府建立联系，了解当地对南宁市技术的技术需求。如：2015年7月，示范基地共组织12家企业及相关机构参加由中国—东盟技术转移中心（CATTC）与缅甸科技部研究与创新司（DRI）、柬埔寨国家科技委员会（NCOST）和柬埔寨国际合作机构（CICO）等共同组织举办的“中国—缅甸、中国—柬埔寨现代农业及新能源技术对接会”，对接成功合作意向项目共40个。";
                technicalRequirement = "      4、紧密联合科技服务机构，拓宽科技中介服务平台\n" +
                        "引入技术转化、法律、知识产权等中介服务机构，如广西博士海意信息科技有限公司、广西创想律师事务所、北京海虹嘉城知识产权代理公司南宁分公司等，利用其社会资源和主营业务上的优势、专业知识能力，为企业提供相关科技服务，拓宽示范基地科技成果转化服务功能。\n" +
                        "示范基地与广西博士海意信息科技有限公司合作运维国家科技成果转化（南宁）综合信息服务平台，借助博士公司科技服务业务团队人资力量、专业知识、丰富经验等资源，联合制作信息平台宣传册，并推送共计500多册，提升平台知晓度；筛选平台不同领域技术信息，走访80多家企业、院所，推送科技成果200多项，提高了信息平台资源有效利用的同时，间接为企业提供咨询、对接、推介等其他中介服务。";
            }else if(baseAddress.equals("宝鸡")){
                baseDynamics = "      宝鸡是西北工业重镇，作为国家科技成果转化服务示范基地，四年来，宝鸡用更加开放的视角，促进科技资源的流动，从搭建平台、聚集资源、对接服务等方面集结发力，推动示范基地建设。\n" +
                        "平台\n" +
                        "“科化网”是示范基地依托的重要平台，拥有科技成果、技术专家、科研机构三大基础数据库，其根据宝鸡产业特色延伸引进了：“国家级装备制造业资源数据库、国家级钛及钛合金资源数据库、国家级电子信息资源数据库、国有级新能源资源数据库”等四大成果数据库和“国家级汽车制造科技知识库”“国家级节能环保科技知识库”两大知识库。有了这个平台，宝鸡的企业得到技术咨询、成果发布、需求发布、线上交流、资源下载等科技服务，让数据“网上跑”，企业得“先风之气”，有效对接需求。\n" +
                        "陕西省凤县盛源铅锌选矿厂需要购买一部先进的选矿设备，要保证矿物的回收率高而且环保节能。负责人何霄在国家科技成果转化（宝鸡）综合信息服务平台上注册会员，发布了需求信息，几天后，江西赣州市企业技术创新促进中心就与“科化网”平台取得联系，企业顺利购进性价高、技术更为先进的选矿设备，使企业得到有力的技术支持。\n" +
                        "据了解，该平台吸引注册会员208个，累计引进国家级科技成果资源数据库权威数据信息41705条，会员在供需平台累计发布信息373条。";
                workEffectiveness = "近几年来，宝鸡市委市政府坚持“抓好大人才、促进大发展”的工作理念，在全市开展校地合作和“一把手”带头进高校、进院所活动，推进与高校院所的人才交流培养和技术项目合作，借智借力推进科技创新及科技成果转化。\n" +
                        "这中间促成了高校院所与企业“甜蜜恋爱”的例子不胜枚举。秦川集团这几年，单和西安交大合作国家的863计划项目和重大科技专项就有12项。中铁电气化宝鸡器材公司与西南交大、西安交大等这一行业的顶尖人才搞共同研究，开发出一大批具有国际先进水平的接触网零部件，企业拥有大张力齿轮补偿装置等20多项有自主知识产权的关键技术，是全球铁路网接触市场最受青睐的企业。\n" +
                        "宝鸡南车时代工程机械有限公司，先后与中国铁道科学院、兰州交通大学、北京交通大学等院所及美国、德国等著名公司开展技术合作，生产的钢轨探伤车、道岔运输车等六大铁路工程机械设备，占国内市场40％的份额。\n" +
                        "近年来，宝鸡与西安交通大学、西北工业大学等高校举办大型产学研对接会，还开展企业老总院校行、院士、专家宝鸡行活动。通过这些活动的开展，该市共有160户企业与高校院所建立了稳定的产学研合作关系，产学研项目实施后预期经济效益超过500亿元。";
                technicalRequirement = "      宝鸡搞成果转化的思路“站位高”，他们与其他国家科技成果转化服务示范基地、国内各创新驿站站点开展广泛合作，与厦门、青岛、南宁等12家国家科技成果转化服务示范基地建立了成果转化工作协作关系，与上海技术交易所、成都生产力促进中心、天水市科技局签订了业务合作框架协议，同北京国家技术转移中心、四川省科学技术信息研究所等十家站点开展协同合作，形成了跨区域协同合作体系。\n" +
                        "再者他们的手法“接地气”向内挖潜。发展以宝钛集团、宝鸡石油机械有限公司等大中型国企为代表的盟员46家，建立金台区生产力促进中心等6家单位为宝鸡创新驿站分站点，在13个县区建立了技术合同认定登记服务站，与陕西省内19所高校和众多研究院所建立了技术转移业务合作关系，形成区域内技术转移服务体系。\n" +
                        "宝鸡注重上“精细菜”，举办多次科技成果转化推介专家论证咨询会，并且启用企业中的信息员，为企业需求上报信息，搅动了科技信息交流“一池活水”。\n" +
                        "四年来，宝鸡示范基地初步形成了以企业需求为导向、以平台为支撑、“一对一”精准对接、“线上服务”与“线下服务”相结合的科技成果转化服务新模式，为本地500余家企业开展了科技成果转化服务，促成项目对接160余项，全市技术交易额从2012年的12.22亿元增长到23.69亿元。";
            }else if(baseAddress.equals("苏州")){
                baseDynamics = "      苏州示范基地的运营单位苏州市生产力促进中心，增挂苏州市科技镇长团服务中心。该中心的主要职责是：为全市科技镇长团工作提供保障服务，帮助科技镇长团发挥桥梁纽带作用，" +
                        "深入开展产学研合作，促进科技成果转移转化，积极为科技镇长团工作出谋划策，推动科技镇长团工作持续发展。";
                workEffectiveness = "      科技镇长团发源于苏州，推广于全省。选派工作实施以来，一批批科技镇长在协助苏州引进人才、项目和推动科技成果转化上取得丰硕成果。目前，苏州已有八批共665人次专家、教授来苏挂职。仅第七批科技镇长团挂职一年，共走访企业5768家次，邀请4401人次专家开展人才科技对接，促成签订横向合作项目557份，合同金额超过4亿元，实际到账资金超过1.5亿元。" +
                        "协助企业申报省级以上项目843项。协助地方引进各级领军人才291人次，申报科技副总57人，帮助基层培训人才9623人次，推动新建各类开发平台488个，孵化器28个，有效地推动了产学研合作向纵深发展，引导创新要素向一线集聚，得到基层和企业的普遍欢迎。";
                technicalRequirement = "      政府科技部门为企业服务多是通过科技项目经费引导或是结合科技资质的认定为企业进行税收的减免，而没有在企业的运营层面提供深度服务。苏州基地通过搭建“企业创新能力提升诊断服务平台”，引入专业化机构来开展企业诊断服务，从而提升企业创新能力。\n" +
                        "一是，依托国际知名的高端服务机构，如香港生产力促进局、德国GAMI全球先进制造研究所等，学习世界知名企业、五百强企业、科技型企业等国际领先企业的模式做法，针对企业的状况、产品类型、战略方向等情况提出具体的改善性建议，使企业可以明确改善方向，实施改善行动。\n" +
                        "二是，依托一些企业高管个人在生产、管理等方面经验，如精益学会会员等，对企业在质量管理，精益管理，供应链方面快速给出诊断建议，以帮助企业从组织架构，能源消耗，生产运营对本身进行全方位认识，达到产能提升，质量提高，成本下降的目标。\n" +
                        "三是，依托一些在激发员工创新能力方面有特色做法的企业，如苏州创美工艺有限公司，推广中小制造型企业自身在生产实践过程中总结出的创新方法及成功经验。这种诊断方式一方面更接近企业的实际生产情况，容易被企业消化吸收，另一方面价格更为低廉，也更容易被中小企业接受。";
            }else if(baseAddress.equals("成都")){
                baseDynamics = "      成都市是我国西部地区重要的科技中心、金融中心、商贸中心和交通、通信枢纽，在西部地区的战略地位十分突出。近年来，成都市社会、经济发展迅速，城乡统筹成绩显著，经济发展总量不断攀升，经济发展质量日益提高，科技创新成绩显著，产业结构不断优化，高新技术产业和现代服务业发展迅速。2011年，成都GDP已达到6854.58亿元，增长15.2%，总量及增速名列西部地区前列；实现规模以上工业增加值2185.3亿元，增长22.3%，增幅列全国副省级城市第一。良好的社会、经济和产业基础，不仅为推进科技成果转化奠定了坚实的基础，同时也迫切需要更多的科技成果作支撑，尤其是成都市重点发展的高端电子信息、现代生物医药、新材料、新能源、航空航天等战略性新兴产业，更加迫切需要大批高水平的科技成果。\n" +
                        "      成都国家级高新技术产业开发区综合排名位居全国国家级高新区中第5位。2010年全市高新技术产业实现产值2070.4亿元，规模以上的高新技术产业共有企业960家。以汽车产业、新能源、新材料等为核心的3个“千亿”产业集群正在形成。按照成都市统筹城乡的战略规划和 “一区一主业”的产业定位，全市21个工业集中发展区功能健全，配套完善，形成了电子信息、软件、汽车、生物医药、航空航天等12个现代产业集群，新能源、新材料、核技术等战略性新兴产业正在加速发展。2010年，成都市工业集中度达到了70.2%，主导产业与配套产业协调发展，高新技术产品制造业与高端服务业协同发展，较为完备的现代产业体系正在加快形成和不断完善。随着成都市高新技术产业和战略性新兴产业的加速发展，迫切需要大量高质量的科技成果作支撑。";
                workEffectiveness = " 近年来，成都市科技投入持续增长，企业科技投入意识日益增强。据统计，2010年全市财政R&D经费内部支出合计达139.54亿元，全市高新技术企业R&D经费支出108.8亿元，企业R&D经费投入占总收入比例达到约3%，在整个科技投入中占主导地位。全口径地方财政科技拨款达到10.7亿元，市本级地方财政科技拨款3.77亿元。科技与金融结合日益紧密，有效的促进了科技成果转化和科技型中小企业的发展。2008年率先在全国设立了2家科技专营支行——成都银行科技支行和建设银行科技支行，相继开展了知识产权质押和科技保险试点工作。截止2010年，40多家科技型中小企业通过知识产权质押获得贷款超过1亿元，130多家企业参加了科技保险，保险额度近200亿元。建立了成都兴蓉创业投资引导基金、银科创业投资引导基金、成都工投创新投资引导基金。先后引进ARC、凯雷、VIVO、深创投、美国黄石基金、德同资本等20余家国内外知名投资机构和投资管理机构入驻，注册资本超过30亿元。与近百家投融资机构签订战略合作协议，聚集资本金额超过100亿元。科技投入持续加强，科技金融活动日趋频繁，知识产权质押、风险投资、知识产权服务、政策及管理咨询等需求越来越迫切，亟需能整合各方资源、围绕成果转化开展业务的服务体出现。";
                technicalRequirement = "      成都示范基地采用政府引导、市场化运作的建设模式。基地决策机构为理事会，其主要职责为：确定“示范基地”的发展方向、发展战略和发展目标，同时对基地内各专业性科技成果转化服务企业的工作进行指导、协调和监督等。“示范基地”运营主体为基地平台公司，采取定向邀请的方式，吸引政府、社会等各方投资共同组建，并建立股权激励机制，促进运营公司高效快速发展。\n" +
                        "      示范基地同时还将通过一定的政府扶持政策，如房屋租金优惠、地方税收按比例先征后返、贷款优先、专项资金扶持等，定向邀请、引进各类有实力的专业性科技服务机构入驻。";
            }else if(baseAddress.equals("沈阳")){
                baseDynamics = " 2012年9月26日下午，国家科学技术奖励办公室在北京召开了“国家科技成果转化服务(沈阳)示范基地”项目建设方案论证会，项目顺利通过专家评审。\n" +
                        "国家科学技术奖励工作办公室张木副主任、沈阳市科技局宋锡坤局长、沈阳泗水科技新城管委会主任田东泉、沈阳联合产权交易所王琳琳主任、国家科技成果网负责人吴昌权主任出席会议。";
                workEffectiveness = "会议由国家科学技术奖励工作办公室成果处姚昆仑处长主持。沈阳市科技局宋锡坤局长向专家组介绍沈阳近年来的科技发展情况，他表示，沈阳具有较好的科技成果转化基础，建设“国家科技成果转化服务（沈阳）示范基地” 对促进老工业基地区域创新和加快国家新型工业化综合配套改革实验区进程具有重要的现实和长远意义。会上，沈阳示范基地项目建设单位项目负责人分别向专家组汇报“示范基地建设总体方案”和“综合信息服务平台可行性报告”。";
                technicalRequirement = "与会专家对“国家科技成果转化服务（沈阳）示范基地”项目进行了论证。专家组审阅了项目可行性报告及相关资料，认真听取了项目建设单位的汇报，经过讨论，认为：国家科技成果转化服务（沈阳）示范基地建设方案设计合理、目标明确、重点突出、措施有力、总体可行，成果转化基地建设已具备较好的实施条件。专家组一致同意“国家科技成果转化服务（沈阳）示范基地”总体方案通过论证。(国科网报道)";
            }else if(baseAddress.equals("北京")){
                baseDynamics = "2012年9月26日上午，国家科学技术奖励办公室在北京召开“国家科技成果转化服务示范基地专家论证会”，对北京示范基地项目进行可行性论证。\n" +
                        "会议由国家科学技术奖励工作办公室成果处姚昆仑处长主持。国家科学技术奖励工作办公室张木副主任、北京市科学技术委员会农村处马金旺副处长、北京农业信息技术研究中心赵春江主任、国家科技成果网负责人吴昌权主任出席会议。";
                workEffectiveness = "评审会上，北京市科学技术委员会农村处副处长马金旺表示，北京创新资源密集，创新需求与活力强劲，建设国家科技成果转化服务（北京）示范基地对建设区域创新体系，加快科技和经济社会紧密结合具有重要意义。";
                technicalRequirement = "北京示范基地项目负责人分别向专家组汇报“国家科技成果转化服务示范基地”建设方案。专家听取了项目建设单位的汇报，并进行了认真讨论会，认为：项目前期工作基础良好，保障有力。总体建设方案以农业科技成果转化为重点，立足北京，辐射全国，目标明确、特色明显、措施得力。项目承担单位在信息技术研发与服务方面具有较强的实力。专家一致认为项目建设方案可行，同意该方案通过论证。(国科网报道)";
            }else if(baseAddress.equals("锦州")){
                baseDynamics = "国科网济南5月31日电 今天上午，“国家科技成果转化服务（济南）示范基地”授牌仪式在山东华艺集团隆重举行。会议由济南市政府副秘书长孙法星主持。国家科学技术奖励工作办公室主任邹大挺、副主任张木，济南市政府副市长巩宪群，山东省科技厅副厅长徐茂波，市政协副主席、市科技局局长冯光文，历下区区委书记雷天太出席仪式。巩宪群副市长、徐茂波副厅长和张木副主任分别致辞。";
                workEffectiveness = "会上，举行了国家最新科技成果发布仪式，发布会由国家科技成果网吴昌权主任主持，国家科学技术奖励工作办公室成果处处长姚昆仑发表重要讲话。北京太阳能研究所和山东建筑大学代表分别在发布会上进行了项目推介。（国科网 吴哲现场报道）";
                technicalRequirement = "会上，举行了国家最新科技成果发布仪式，发布会由国家科技成果网吴昌权主任主持，国家科学技术奖励工作办公室成果处处长姚昆仑发表重要讲话。北京太阳能研究所和山东建筑大学代表分别在发布会上进行了项目推介。（国科网 吴哲现场报道）";
            }else if(baseAddress.equals("银川")){
                baseDynamics = "经过两年的建设，2014年10月17日，银川科技园正式启动运行。启动仪式上，宁夏自治区党委常委、银川市委书记徐广国，中科院西安分院院长赵卫，国家科学技术奖励工作办公室张木副主任等为“中国科学院银川科技创新与产业育成中心”、“国家科技成果转化服务（银川）示范基地”、“浙江大学银川技术转移中心”和“银川产业情报研究中心”揭牌。";
                workEffectiveness = "建设银川科技园是破解科技创新能力薄弱、科技人才缺乏等制约银川长远发展瓶颈的重大举措，银川科技园于2012年8月正式动工建设，截至目前完成投资10亿元，已基本完成园区道路、水系、绿化及景观工程建设，科技大厦、科技园规划展示馆，中科院研发大楼建成并投入使用。科技园采取边建设、边招商的工作思路，与中科院、清华大学、浙江大学等科研机构、高等院校开展了多层次科技交流与合作。科技园以引入一流的科研机构、依托一流的科技创新人才、组建一流的科技创新团队、开展一流的科技创新活动，打造成为西北一流的科技创新资源聚集高地、科技创新政策密集凹地、科技创新人才汇集高地。";
                technicalRequirement = "建设银川科技园是破解科技创新能力薄弱、科技人才缺乏等制约银川长远发展瓶颈的重大举措，银川科技园于2012年8月正式动工建设，截至目前完成投资10亿元，已基本完成园区道路、水系、绿化及景观工程建设，科技大厦、科技园规划展示馆，中科院研发大楼建成并投入使用。科技园采取边建设、边招商的工作思路，与中科院、清华大学、浙江大学等科研机构、高等院校开展了多层次科技交流与合作。科技园以引入一流的科研机构、依托一流的科技创新人才、组建一流的科技创新团队、开展一流的科技创新活动，打造成为西北一流的科技创新资源聚集高地、科技创新政策密集凹地、科技创新人才汇集高地。";
            }else if(baseAddress.equals("十堰")){
                baseDynamics = "十堰基地创建以来，积极组织本市企业与北京、武汉大专院校、科研院所及有关大型企业开展科技对接活动，带领企业参加“科博会”、“华创会”、专利成果洽谈会等，促成企业与高校院所签定了一大批技术协作、联合开发、联建技术中心和产业基地的合作协议，有力助推了十堰市科技创新服务体系建设。\n" +
                        "1、促进校企合作共建了一批研发机构。十堰基地组织多家企业与北京、武汉高校院所开展科技对接，联建、共建、委托组建了一批不同形式的技术创新平台。如湖北丹澳药业有限公司与武汉工程大联合共建十堰市药用甾体化合物校企共建研发中心、十堰市东风汽车精工齿轮厂与华中科技大学学联合共建了湖北省精密锻造成形技术校企共建研发中心。2015年十堰市被认定的省级工程技术中心、省级校企共建研发中心、市级校企共建研发中心近50家。\n" +
                        "2、组织申报了一批国家高新技术企业。为进一步提升十堰市企业整体素质，提高企业技术创新转化应用能力，打造企业科技品牌，十堰基地组织十堰市科技型企业申报高新技术企业，截至目前，十堰市高新技术企业规模总量达到140家，在全省排在第4位，高新技术企业数量占规模以上企业比例保持在全省前列。近3年保持了每年新增30家的势头。\n" +
                        "3、推动了科技孵化器体系建设。科技孵化器是培养科技企业和企业家的摇篮，是推动企业开展技术创新和加速成果应用的载体。2015年，通过多方努力，十堰基地已组织新建科技孵化器2家，获批省级科技孵化器1家，特别是重点推进了各县市区科技孵化器体系建设。\n" +
                        "4、初步建立区域科技服务体系。在市科技局支持下，十堰市科技特派员队伍逐步扩大，服务管理体系逐步完善。十堰基地组织科技特派员与用人单位签订了三方协议，派驻科技人员驻点展开科技服务工作，每人每年给予2万元财政专项补贴。2015年十堰基地共组织签约了63名科技特派员，示范基地已搭建科技特派员库，对外公示其擅长技术领域与服务区域，方便科技人员与用人单位对接。";
                workEffectiveness = "1、十堰科技创新中心主体建设基本完成。作为十堰基地的重要活动载体，位于十堰工业新区的十堰市科技创新中心主体工程已于2015年5月份竣工完成，总建筑面积7150㎡，总投资2000万元。目前，国家科技成果转化服务（十堰）示范基地已在十堰科技创新中心大楼挂牌，大楼建有技术交易服务大厅，已经遴选了10余家十堰科技服务机构，即将入驻，为技术交易提供科技、金融、政策、咨询等优质服务。";
                technicalRequirement = "2、政产学研资介互动合作平台初步搭建。在市区科技部门的支持下，十堰基地深入各类企业开展技术需求调研，征集企业技术、人才和融资需求，同时基地又积极跟大专院校互动交流，组织专家跟企业产学研对接，为政产学研资介合作平台的搭建奠定了良好的基础。三年来，十堰基地先后开展技术需求调研80余次，征集企业技术需求100余项，开展产学研对接活动8次，为企业提供成果转化项目咨询50余次，特别是组织完成了沃优生物、万向通达、正和车身等科技成果转化项目80项。\n" +
                        "3、北京十堰交流合作更加热络。技术和人才交流是北京十堰对口协作的重要基础。借助南水北调对口协作机遇，十堰基地不断加强跟北京的对口单位交流合作，进一步助推了北京十堰科技成果一体化。自2013年以来,十堰基地联手北京市科委、中关村管委会先后组织了十余次专家领导十堰行，积极来十堰开展企业调研、成果推介、供需对接等活动。如2015年6月24日，北京市科委陈云波处长带队来堰，开展了首都科技条件平台十堰成果发布及对接会。与此同时，十堰基地先后5次组织人员赴北京参加培训学习交流活动。";
            }else if(baseAddress.equals("太原")){
                baseDynamics = " 2012年9月24日下午，国家科学技术奖励工作办公室在北京组织召开“国家科技成果转化服务示范基地专家论证会”，对宁夏示范基地项目进行可行性论证。";
                workEffectiveness = "会议由国家科学技术奖励工作办公室成果处姚昆仑处长主持。国家科学技术奖励工作办公室张木副主任、宁夏回族自治区科技厅发展计划处陈国顺处长、项目建设单位宁夏银星能源光伏发电设备制造有限公司副总经理丁国安、合作单位国家科技成果网负责人吴昌权主任，其他项目申报单位和承担单位的人员出席会议。以科技部发展计划司原巡视员申茂向为专家组组长的七位科技科技管理、成果转化及相关领域的资深专家参加了论证会。";
                technicalRequirement = "评审会上，宁夏示范基地项目负责人分别向专家组汇报“国家科技成果转化服务示范基地”建设方案和“综合信息服务平台可行性报告”。 专家听取了项目建设单位的汇报，并进行了认真讨论，认为，宁夏示范基地建设的总体方案科学合理，目标任务明确，保障措施有力，进度安排可行。对探索以政府为指导、以企业为主体的科技成果转化机制具有现实和战略意义。项目承担单位具备较好基础条件，合作单位国家科技成果网等具有较好的技术优势和信息资源。专家组一致同意项目实施方案，建议立项。（国科网报道）";
            }else if(baseAddress.equals("贵阳")){
                baseDynamics = "近日，国家科学技术奖励工作办公室正式复函同意贵阳市、青岛市依托国家科技成果网，分别建设国家科技成果转化服务(贵阳)示范基地和（青岛）示范基地。";
                workEffectiveness = "创建国家科技成果转化服务示范基地，是国家科技奖励办为进一步促进科技成果转化为现实生产力而采取的战略性举措。建设“国家科技成果转化服务（贵阳）示范基地”，对加速贵阳以铝及铝加工、磷煤化工、现代药业、特色食品加工为主的特色支柱产业的发展，推动当地的经济结构调整和产业升级，推进先进适用技术向贵阳及周边地区转移等方面具有重要意义和积极作用。建设“国家科技成果转化服务（青岛）示范基地”，将充分发挥青岛市先进装备制造产业、海洋科技等领域的区位优势和产业特色，提高区域的技术创新能力和产业竞争力。";
                technicalRequirement = "下一步，两地将充分利用国家科技成果库中的项目资源，从中筛选适合贵阳、青岛转化的项目，促成市场前景较好的科技成果在贵阳、青岛转化和产业化，为企业技术创新提供支撑。同时，以国家科技成果转化服务示范基地建设为契机，按照“政府引导、市场配置”的理念，从企业技术需求出发，充分借助信息化和科技中介服务机构的力量，构建全链条、多要素的科技成果转移对接和配套服务体系。";
            }else if(baseAddress.equals("青岛")){
                baseDynamics = "2014年10月29日，国家科技成果转化服务示范基地座谈会在山东青岛召开。国家科学技术奖励工作办公室张木副主任出席会议并讲话，国家奖励办成果处王谋勇副处长、姚昆仑调研员以及来自各成果转化服务示范基地、各有关科技厅、局及国家科技成果网的40多名代表出席了会议。";
                workEffectiveness = "会上，济南、苏州、南宁、宝鸡、十堰、贵阳、太原、锦州、北京、成都、沈阳、银川、厦门、青岛14家成果转化服务示范基地以及昆明、重庆等申报单位的代表分别介绍了各示范基地开展科技成果推广转化服务的做法、经验和体会及下一步的工作计划。\n" +
                        "2010年启动国家科技成果转化服务示范基地建设工作以来，已经批复建设的14个示范基地累计服务企业1.5万家，对接项目5千个，落地项目100个，培训技术经纪人1600人。示范基地建设工作开展以来取得了一定成绩。张木副主任要求，各个示范基地下一阶段工作应不断探索和创新服务模式，完善并提升科技成果转化服务能力，积极发挥示范基地的示范带动效应。";
                technicalRequirement = "座谈会上，国家科技成果网介绍了面向示范基地的支撑服务工作安排。为推进示范基地的建设，国家科技成果网重点围绕“面上服务”、“重点服务”和“对接服务”，为各示范基地定制提供国家科技成果筛选系统，定制推荐科研项目、专家，组织多种形式的对接会等服务。\n" +
                        "与会人员还就下一步如何更好地开展示范基地的建设做了交流，提出了有益的意见和建议。\n" +
                        "本次座谈会还邀请投融资专家，就科技成果转化模式、科技成果转化基金运作和相关案例，为参会代表进行详尽介绍。（国家科技成果网）";
            }else if(baseAddress.equals("甘肃")){

            }
            modelMap.put("baseDynamics",baseDynamics);
            modelMap.put("workEffectiveness",workEffectiveness);
            modelMap.put("technicalRequirement",technicalRequirement);
            modelMap.put("baseAddress",baseAddress);
            return "jsp-front/base-detail";
        }catch(Exception e){
            log.error(timeToken+"进入baseDetail的catch方法,异常信息:"+e.getMessage());
            return "jsp-error/error-page";
        }
    }

}
