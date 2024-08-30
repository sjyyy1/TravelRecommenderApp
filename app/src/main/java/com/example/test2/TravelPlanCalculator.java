
package com.example.test2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
//import com.example.test2.Hotel;


public class TravelPlanCalculator {
    private List<TravelQuestion> questions;
    private List<Object> answers; // 存储单选和多选答案的列表

    // 定义片区与选项的映射
    private Map<Integer, String[]> shoppingZones;
    private List<Hotel> hotels; // 所有酒店信息
    private List<Attraction> attractions;
    private List<Restaurant> restaurants;
    private Context context; // 添加上下文

    public TravelPlanCalculator(List<TravelQuestion> questions, Context context) {
        this.questions = questions;
        this.context = context; // 初始化上下文
        answers = new ArrayList<>(questions.size());
        for (int i = 0; i < questions.size(); i++) {
            answers.add(null);
        }

        // 初始化片区数据
        initializeZones();
    }

    private void initializeZones() {
        restaurants = new ArrayList<>();
        restaurants.add(new Restaurant("百年前门铜锅涮肉（王府井4点店）",
                new String[]{"天安门广场", "中国国家博物馆", "故宫博物院", "中山公园", "王府井步行街", "中国美术馆", "景山公园", "北大红楼", "国家大剧院", "老舍故居"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("四季民福烤鸭店（故宫店）",
                new String[]{"天安门广场", "中国国家博物馆", "故宫博物院", "中山公园", "王府井步行街", "中国美术馆", "景山公园", "北大红楼", "国家大剧院", "老舍故居"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("北平食府臻品私宴",
                new String[]{"天安门广场", "中国国家博物馆", "故宫博物院", "中山公园", "王府井步行街", "中国美术馆", "景山公园", "北大红楼", "国家大剧院", "老舍故居"}, "本地风味", "200元以上"));
        restaurants.add(new Restaurant("老刘铜锅涮肉（前门店）",
                new String[]{"天安门广场", "中国国家博物馆", "故宫博物院", "中山公园", "王府井步行街", "中国美术馆", "景山公园", "北大红楼", "国家大剧院", "老舍故居", "李大钊故居"}, "本地风味", "50元以下"));
        restaurants.add(new Restaurant("紫光园（崇文门）",
                new String[]{"天安门广场", "中国国家博物馆", "故宫博物院", "中山公园", "王府井步行街", "中国美术馆", "景山公园", "北大红楼", "国家大剧院", "老舍故居", "茅盾故居"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("庭院江南菜（王府井店）",
                new String[]{"天安门广场", "中国国家博物馆", "故宫博物院", "中山公园", "王府井步行街", "中国美术馆", "景山公园", "北大红楼", "国家大剧院", "老舍故居"}, "外地风味", "50~200元"));
        restaurants.add(new Restaurant("尹三豆汁（前门旗舰店）",
                new String[]{"故宫博物院", "王府井步行街", "中国美术馆", "景山公园", "天坛公园", "国家自然博物馆", "北京明城墙遗址公园", "北京人民艺术剧院", "茅盾故居", "长安大戏院"}, "本地风味", "50元以下"));
        restaurants.add(new Restaurant("北门伊信斋",
                new String[]{"故宫博物院", "王府井步行街", "中国美术馆", "景山公园", "天坛公园", "国家自然博物馆", "北京明城墙遗址公园", "北京人民艺术剧院", "茅盾故居", "长安大戏院"}, "外地风味", "50~200元"));
        restaurants.add(new Restaurant("全聚德（清华园店）",
                new String[]{"清华大学", "北京大学"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("聚宝源（五道口购物中心店）",
                new String[]{"清华大学", "北京大学"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("门框胡同百年卤煮（中关村店）",
                new String[]{"清华大学", "北京大学"}, "本地风味", "50元以下"));
        restaurants.add(new Restaurant("酒本居酒屋（西苑店）",
                new String[]{"圆明园遗址公园", "颐和园", "百望山森林公园", "北京大学"}, "外地风味", "50~200元"));
        restaurants.add(new Restaurant("洞庭楚湘（香山颐和店）",
                new String[]{"圆明园遗址公园", "颐和园", "百望山森林公园", "香山公园"}, "外地风味", "50~200元"));
        restaurants.add(new Restaurant("肆月河豚（中关村店）",
                new String[]{"圆明园遗址公园", "颐和园", "百望山森林公园"}, "外地风味", "50~200元"));
        restaurants.add(new Restaurant("吉野家（龙背村）",
                new String[]{"圆明园遗址公园", "颐和园", "百望山森林公园"}, "外地风味", "50元以下"));
        restaurants.add(new Restaurant("比格比萨自助（肖家河店）",
                new String[]{"圆明园遗址公园", "颐和园", "百望山森林公园"}, "外地风味", "50~200元"));
        restaurants.add(new Restaurant("成隆粤宴（国际会议中心店）",
                new String[]{"北京奥林匹克公园"}, "外地风味", "200元以上"));
        restaurants.add(new Restaurant("小吊梨汤（新奥购物中心）",
                new String[]{"北京奥林匹克公园"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("北平盛世（亚运村鸟巢店）",
                new String[]{"北京奥林匹克公园"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("小厨娘淮扬菜（蓝色港湾店）",
                new String[]{"朝阳公园", "七棵树创意园"}, "外地风味", "200元以上"));
        restaurants.add(new Restaurant("王府茶楼茶宴（朝阳公园店）",
                new String[]{"朝阳公园", "七棵树创意园"}, "本地风味", "200元以上"));
        restaurants.add(new Restaurant("正泰泰园ThaiGarden（蓝色港湾店）",
                new String[]{"朝阳公园", "七棵树创意园"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("紫光园（枣营店）",
                new String[]{"朝阳公园", "七棵树创意园"}, "本地风味", "50元以下"));
        restaurants.add(new Restaurant("书湘门地（西苑店）",
                new String[]{"北京动物园", "北京海洋馆", "北京天文馆"}, "外地风味", "200元以上"));
        restaurants.add(new Restaurant("湘香小厨（魏公村总店）",
                new String[]{"北京动物园", "北京海洋馆", "北京天文馆"}, "外地风味", "50元以下"));
        restaurants.add(new Restaurant("双福园（双榆树店）",
                new String[]{"北京动物园", "北京海洋馆", "北京天文馆"}, "外地风味", "50元以下"));
        restaurants.add(new Restaurant("大鸭梨烤鸭店（土桥店）",
                new String[]{"北京环球度假区"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("天和晟烤鸭店（世纪坛店）",
                new String[]{"玉渊潭公园", "北京动物园"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("苏皖湘",
                new String[]{"玉渊潭公园", "北京动物园"}, "外地风味", "50~200元"));
        restaurants.add(new Restaurant("粤港轩新粤菜（木樨地）",
                new String[]{"玉渊潭公园", "北京动物园", "白云观", "首都博物馆"}, "外地风味", "200元以上"));
        restaurants.add(new Restaurant("庭院江南菜（国贸店）",
                new String[]{"今日美术馆"}, "外地风味", "50~200元"));
        restaurants.add(new Restaurant("鲁采赋（北京CBD光华路店）",
                new String[]{"今日美术馆"}, "外地风味", "200元以上"));
        restaurants.add(new Restaurant("北山居隐（八达岭长城店）",
                new String[]{"八达岭长城", "八达岭野生动物世界", "居庸关长城"}, "外地风味", "50~200元"));
        restaurants.add(new Restaurant("石茑庭院餐厅（八达岭店）",
                new String[]{"八达岭长城", "八达岭野生动物世界", "居庸关长城"}, "外地风味", "50~200元"));
        restaurants.add(new Restaurant("庆丰包子铺（八达岭二店）",
                new String[]{"八达岭长城", "八达岭野生动物世界", "居庸关长城"}, "本地风味", "50元以下"));
        restaurants.add(new Restaurant("北门涮肉铜锅涮肉",
                new String[]{"北海公园", "景山公园", "南锣鼓巷", "敕建火德真君庙", "恭王府博物馆", "鼓楼", "郭沫若故居", "后海公园", "后海酒吧一条街", "护国寺街", "梅兰芳大剧院", "宋庆龄同志故居", "烟袋斜街"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("淄味淄博烧烤",
                new String[]{"景山公园", "故宫博物院", "王府井步行街"}, "外地风味", "50元以下"));
        restaurants.add(new Restaurant("东来顺饭庄(铭泽生活广场）",
                new String[]{"北京欢乐谷"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("合欢谷（北京燕莎奥特莱斯购物中心）",
                new String[]{"北京欢乐谷"}, "外地风味", "50元以下"));
        restaurants.add(new Restaurant("聚点串吧（北京簋街店）",
                new String[]{"雍和宫", "孔庙和国子监博物馆", "北京簋街"}, "外地风味", "50~200元"));
        restaurants.add(new Restaurant("赛百味（北京簋街店）",
                new String[]{"雍和宫", "孔庙和国子监博物馆", "北京簋街"}, "外地风味", "50元以下"));
        restaurants.add(new Restaurant("紫光园(高家元店)",
                new String[]{"798艺术区", "红砖美术馆"}, "本地风味", "50元以下"));
        restaurants.add(new Restaurant("懂羊帝·羊蝎子(798艺术区店）",
                new String[]{"798艺术区", "红砖美术馆"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("肆月河豚·宋韵淮扬旗舰店(酒仙桥店)",
                new String[]{"798艺术区", "红砖美术馆"}, "本地风味", "200元以上"));
        restaurants.add(new Restaurant("陕西驻京办餐厅",
                new String[]{"白云观", "首都博物馆"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("八先生涮肉房(西城店)",
                new String[]{"白云观", "首都博物馆"}, "本地风味", "200元以上"));
        restaurants.add(new Restaurant("方砖厂69号炸酱面(南锣鼓巷店）",
                new String[]{"北海公园", "景山公园", "南锣鼓巷", "敕建火德真君庙", "恭王府博物馆", "鼓楼", "郭沫若故居", "后海公园", "后海酒吧一条街", "梅兰芳大剧院", "宋庆龄同志故居", "烟袋斜街"}, "本地风味", "50元以下"));
        restaurants.add(new Restaurant("楚令尹·现代湘菜(北京坊店)",
                new String[]{"北京大栅栏", "北京古代建筑博物馆", "天坛公园", "陶然亭公园", "国家自然博物馆", "前门大街"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("黑窑厂糖油饼羊蝎子(黑窑厂街店）",
                new String[]{"北京古代建筑博物馆", "天坛公园", "陶然亭公园", "国家自然博物馆", "护国寺街"}, "本地风味", "50元以下"));
        restaurants.add(new Restaurant("四世同堂·京菜·烤鸭(十里河店）",
                new String[]{"北京欢乐谷"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("紫光园(红庙45号店)",
                new String[]{"北京欢乐谷"}, "本地风味", "50元以下"));
        restaurants.add(new Restaurant("景福四会院",
                new String[]{"北京欢乐谷"}, "本地风味", "200元以上"));
        restaurants.add(new Restaurant("古铜老院铜锅涮肉·清真(西四店）西城区西四北大街",
                new String[]{"北京历代帝王庙博物馆", "北京鲁迅博物馆", "西什库天主堂", "西直门天主堂"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("护国寺小吃(阜桥店)",
                new String[]{"北京历代帝王庙博物馆", "北京鲁迅博物馆", "西什库天主堂", "西直门天主堂"}, "本地风味", "50元以下"));
        restaurants.add(new Restaurant("小吊梨汤(香山店)",
                new String[]{"北京西山国家森林公园", "国家植物园", "香山公园"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("山海京·国潮新京鲁(香山店)",
                new String[]{"北京西山国家森林公园", "国家植物园", "香山公园"}, "本地风味", "200元以上"));
        restaurants.add(new Restaurant("观湖餐厅",
                new String[]{"北京野生动物园"}, "外地风味", "50~200元"));
        restaurants.add(new Restaurant("陈记老北京铜锅涮肉(榆垡店)",
                new String[]{"北京野生动物园"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("聚宝源(东单店)",
                new String[]{"蔡元培故居"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("护国寺小吃(东单分店)",
                new String[]{"蔡元培故居"}, "本地风味", "50元以下"));
        restaurants.add(new Restaurant("赣南人家·庭院餐厅(右安门店）",
                new String[]{"大观园"}, "外地风味", "50~200元"));
        restaurants.add(new Restaurant("城南居老北京小吃店(北电大夏店）",
                new String[]{"大观园"}, "本地风味", "50元以下"));
        restaurants.add(new Restaurant("和合谷(通州潞城店)",
                new String[]{"大运河森林公园"}, "本地风味", "50元以下"));
        restaurants.add(new Restaurant("天和晟烤鸭店(通州朗清园店)",
                new String[]{"大运河森林公园"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("隐海·渔芙南",
                new String[]{"北海公园", "景山公园", "南锣鼓巷", "敕建火德真君庙", "恭王府博物馆", "鼓楼", "郭沫若故居", "后海公园", "后海酒吧一条街", "护国寺街", "梅兰芳大剧院", "宋庆龄同志故居"}, "外地风味", "200元以上"));
        restaurants.add(new Restaurant("提督·TIDU(北京坊店)",
                new String[]{"北京古代建筑博物馆", "天坛公园", "陶然亭公园", "国家自然博物馆", "李大钊故居", "前门大街"}, "外地风味", "200元以上"));
        restaurants.add(new Restaurant("岩餐厅",
                new String[]{"红螺寺", "雁栖湖"}, "外地风味", "50~200元"));
        restaurants.add(new Restaurant("人民公社铁锅队八大队",
                new String[]{"红螺寺", "雁栖湖"}, "外地风味", "50元以下"));
        restaurants.add(new Restaurant("京城北门羊蝎子铜锅涮肉(金融街融悦汇商业中心店)",
                new String[]{"戒台寺景区"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("巧阿婆春饼(晓月苑店)",
                new String[]{"卢沟桥文化旅游区"}, "外地风味", "50元以下"));
        restaurants.add(new Restaurant("宛平盛世老北京烤鸭店（古桥物业商务中心店）",
                new String[]{"卢沟桥文化旅游区"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("海棠八馆",
                new String[]{"明十三陵景区"}, "外地风味", "50~200元"));
        restaurants.add(new Restaurant("聚客来春饼宴",
                new String[]{"明十三陵景区"}, "外地风味", "50元以下"));
        restaurants.add(new Restaurant("翼松楼餐厅",
                new String[]{"慕田峪长城"}, "外地风味", "50~200元"));
        restaurants.add(new Restaurant("馅老满（慕田峪店）",
                new String[]{"慕田峪长城"}, "本地风味", "50元以下"));
        restaurants.add(new Restaurant("聚宝源（牛街总店）",
                new String[]{"牛街"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("城一锅（世界公园店）",
                new String[]{"世界公园"}, "本地风味", "200元以上"));
        restaurants.add(new Restaurant("东来顺（世界公园店）",
                new String[]{"世界公园"}, "本地风味", "50~200元"));
        restaurants.add(new Restaurant("徽味大班（酒仙桥店）",
                new String[]{"首创·朗园Station"}, "外地风味", "50~200元"));
        restaurants.add(new Restaurant("汇祥居军嫂水饺自助",
                new String[]{"首创·朗园Station"}, "外地风味", "50元以下"));
        restaurants.add(new Restaurant("悬崖餐厅",
                new String[]{"潭柘寺"}, "外地风味", "200元以上"));
        restaurants.add(new Restaurant("一尺花园（慢闪公园店）",
                new String[]{"潭柘寺"}, "外地风味", "50~200元"));
        restaurants.add(new Restaurant("彩云之滇-云南主题餐厅（宣武46超棒门店）",
                new String[]{"宣武门天主堂"}, "外地风味", "50~200元"));
        restaurants.add(new Restaurant("山乡居",
                new String[]{"玉渡山风景区"}, "外地风味", "50元以下"));
        restaurants.add(new Restaurant("江湖一品灶台鱼（延庆总店）",
                new String[]{"玉渡山风景区"}, "外地风味", "50~200元"));
        restaurants.add(new Restaurant("方砖厂69号炸酱面（北京站南小街店）",
                new String[]{"长安大戏院"}, "本地风味", "50元以下"));
        restaurants.add(new Restaurant("小草屋（望京店）",
                new String[]{"中央美术学院-美术馆"}, "本地风味", "50元以下"));
        restaurants.add(new Restaurant("食悦江南（惠新店）",
                new String[]{"中央美术学院-美术馆"}, "外地风味", "50~200元"));
        restaurants.add(new Restaurant("玉林饭店",
                new String[]{"周口店国家考古遗址公园"}, "外地风味", "50~200元"));
        restaurants.add(new Restaurant("牛肉罩饼（京周路店）",
                new String[]{"周口店国家考古遗址公园"}, "外地风味", "50元以下"));

        if (hotels == null) {
            hotels = new ArrayList<>();
        }
        hotels.add(new Hotel("景里红驿栈酒店(北京东直门三里屯店)", 0, 1, 1));
        hotels.add(new Hotel("北京三里屯CHAO酒店", 0, 1, 2));
        hotels.add(new Hotel("北京东直门亚朵S酒店", 0, 1, 1));
        hotels.add(new Hotel("壹色四合院酒店(北京天安门王府井店)", 0, 1, 2));
        hotels.add(new Hotel("诺宾酒店(北京中关村国家图书馆地铁站店)", 0, 1, 1));
        hotels.add(new Hotel("北京天安门王府井步行街亚朵酒店", 0, 1, 1));
        hotels.add(new Hotel("北京嘉一温馨公寓(儿研所店)", 0, 1, 1));
        hotels.add(new Hotel("北京后海南锣鼓巷CitiGO欢阁酒店", 0, 1, 1));
        hotels.add(new Hotel("香柏艺术酒店(北京南锣鼓巷后海店)", 0, 1, 1));
        hotels.add(new Hotel("COFFIZZ高富诗酒店(北京天安门王府井教堂店)", 0, 1, 1));
        hotels.add(new Hotel("秋果酒店(北京颐和园店)", 0, 1, 1));
        hotels.add(new Hotel("雅悦和美酒店(北京西直门新街口地铁站店)", 0, 1, 1));
        hotels.add(new Hotel("北京万豪行政公寓", 0, 1, 2));
        hotels.add(new Hotel("北京东方花园饭店", 0, 1, 1));
        hotels.add(new Hotel("秋果酒店(北京东直门雍和宫簋街店)", 0, 1, 1));
        hotels.add(new Hotel("清悦酒店(北京中关村理工大学店)", 0, 1, 1));
        hotels.add(new Hotel("云郦酒店(北京南锣鼓巷簋街店)", 0, 1, 1));
        hotels.add(new Hotel("秋果酒店(北京中关村店)", 0, 1, 1));
        hotels.add(new Hotel("北京中关村理工大学漫心酒店", 0, 1, 1));
        hotels.add(new Hotel("北京金龙潭御瑞酒店", 0, 1, 1));
        hotels.add(new Hotel("北方朗悦酒店(北京西单金融街店)", 0, 2, 1));
        hotels.add(new Hotel("北京朝阳路大悦城亚朵酒店", 0, 2, 1));
        hotels.add(new Hotel("桔子水晶北京国贸建国门酒店", 0, 2, 1));
        hotels.add(new Hotel("北京大方瑞廷西郊酒店", 0, 2, 1));
        hotels.add(new Hotel("汉庭酒店(北京前门大街店)", 0, 2, 2));
        hotels.add(new Hotel("北京索菲特大酒店", 0, 2, 2));
        hotels.add(new Hotel("北京凤凰台饭店", 0, 2, 1));
        hotels.add(new Hotel("北京西站六里桥亚朵酒店", 0, 2, 1));
        hotels.add(new Hotel("北京宣武门商务酒店", 0, 2, 1));
        hotels.add(new Hotel("北京东交民巷饭店", 0, 2, 1));
        hotels.add(new Hotel("北京新世界中心同派酒店", 0, 2, 1));
        hotels.add(new Hotel("曼居酒店(北京五道口地铁站店)", 0, 3, 1));
        hotels.add(new Hotel("胜开酒店(北京苏州街地铁站店)", 0, 3, 1));
        hotels.add(new Hotel("北京中关村科技园智选假日酒店", 0, 3, 1));
        hotels.add(new Hotel("北京中关村学院桥地铁站亚朵酒店", 0, 3, 1));
        hotels.add(new Hotel("云郦酒店(北京朝阳公园国贸商务区店)", 0, 4, 1));
        hotels.add(new Hotel("7天连锁酒店(北京鸟巢国家会议中心店)", 0, 4, 1));
        hotels.add(new Hotel("北京燕莎三元桥亚朵酒店", 0, 4, 1));
        hotels.add(new Hotel("布丁严选酒店(北京三里屯团结湖店)", 0, 4, 1));
        hotels.add(new Hotel("北京首都机场东海康得思酒店", 0, 4, 1));
        hotels.add(new Hotel("北京维景国际大酒店", 0, 4, 1));
        hotels.add(new Hotel("北京威斯汀酒店", 0, 4, 2));
        hotels.add(new Hotel("桔子酒店(北京亚运村鸟巢店)", 0, 4, 1));
        hotels.add(new Hotel("北京望京凯悦酒店", 0, 4, 2));
        hotels.add(new Hotel("北京鹏润国际大酒店", 0, 4, 1));
        hotels.add(new Hotel("北京枫烨园酒店", 0, 4, 2));
        hotels.add(new Hotel("北京丽都维景酒店", 0, 4, 1));
        hotels.add(new Hotel("秋果酒店智选(北京朝阳高铁站东坝中路店)", 0, 4, 1));
        hotels.add(new Hotel("北京丽都皇冠假日酒店", 0, 4, 2));
        hotels.add(new Hotel("秋果酒店(北京三元桥使馆区店)", 0, 4, 1));
        hotels.add(new Hotel("北京亮马河饭店", 0, 4, 1));
        hotels.add(new Hotel("一块砖长城遗产民宿", 1, 5, 2));
        hotels.add(new Hotel("北京小喜民宿", 1, 5, 2));
        hotels.add(new Hotel("北京银山宿集", 1, 5, 2));
        hotels.add(new Hotel("花筑·北京怀柔区美自悠然民宿", 1, 5, 1));
        hotels.add(new Hotel("南山里民宿(九渡河店)", 1, 5, 2));
        hotels.add(new Hotel("北京岚山苑沐秋民宿", 1, 5, 1));
        hotels.add(new Hotel("北京点翠客栈", 1, 6, 2));
        hotels.add(new Hotel("北京竹溪度假山庄", 1, 6, 1));
        hotels.add(new Hotel("野生花园·栗子树民宿(怀柔景区店)", 1, 6, 1));
        hotels.add(new Hotel("花筑·北京柒石榴客栈(响水湖店)", 1, 6, 1));
        hotels.add(new Hotel("井邻栗林花园民宿", 1, 6, 2));
        hotels.add(new Hotel("秋果S酒店·1924(北京五棵松解放军301总医院店)", 0, 7, 1));
        hotels.add(new Hotel("北京天天假日御朗酒店", 0, 7, 1));
        hotels.add(new Hotel("北京门头沟MUSTEL木文缇酒店(上岸地铁站店)", 0, 7, 1));
        hotels.add(new Hotel("北京总部基地亚朵酒店", 0, 7, 1));
        hotels.add(new Hotel("北京西站南广场亚朵酒店", 0, 7, 1));
        hotels.add(new Hotel("桔子水晶北京丰台火车站总部基地酒店", 0, 7, 1));
        hotels.add(new Hotel("北京呢喃山居私汤民宿", 1, 7, 2));
        hotels.add(new Hotel("潭柘雅园民宿", 1, 8, 1));
        hotels.add(new Hotel("北京宾旺来客栈", 1, 8, 0));
        hotels.add(new Hotel("北京门头沟MUSTEL木文缇酒店(上岸地)", 0, 8, 1));
        hotels.add(new Hotel("北京休闲驿站快捷酒店", 0, 8, 0));
        hotels.add(new Hotel("京寓公寓(中央民族大学丰台校区店)", 1, 8, 0));
        hotels.add(new Hotel("北京慢时光境酒店", 0, 9, 0));
        hotels.add(new Hotel("喆啡酒店(北京通州张家湾环球度假区店)", 0, 9, 0));
        hotels.add(new Hotel("北京通州北投绿心网球酒店", 0, 9, 1));
        hotels.add(new Hotel("北京Man Yan蔓言酒店", 0, 9, 1));
        hotels.add(new Hotel("享·墅经济型独栋民宿", 1, 9, 0));
        hotels.add(new Hotel("邻舍小院(北京大兴国际机场店)", 1, 10, 0));
        hotels.add(new Hotel("格林豪泰(北京大兴国际机场榆垡镇店)", 0, 10, 0));
        hotels.add(new Hotel("希岸轻雅酒店(固安北京大兴国际机场店)", 0, 10, 0));
        hotels.add(new Hotel("北京大兴机场亚朵X酒店", 0, 10, 1));
        hotels.add(new Hotel("固安大湖观景民宿(大兴机场店)", 1, 10, 0));
        hotels.add(new Hotel("全季酒店(北京大兴机场野生动物园店)", 0, 10, 1));
        hotels.add(new Hotel("非繁城品酒店(北京延庆高铁站店)", 0, 11, 0));
        hotels.add(new Hotel("曾府酒店(北京延庆店)", 0, 11, 1));
        hotels.add(new Hotel("知行雅苑(玉渡山风景区店)", 1, 11, 0));
        hotels.add(new Hotel("北京绍府微度假中心(延庆玉渡山风景区店)", 1, 11, 2));
        hotels.add(new Hotel("北京日出东方驿栈", 1, 11, 0));
        hotels.add(new Hotel("三旬歇脚民宿", 1, 11, 0));
        hotels.add(new Hotel("北京千峋轻舍酒店", 0, 12, 0));
        hotels.add(new Hotel("日出山谷民宿", 1, 12, 1));
        hotels.add(new Hotel("北京天毓山庄", 1, 12, 1));
        hotels.add(new Hotel("北京大家据民宿", 1, 12, 1));
        hotels.add(new Hotel("官地小院", 1, 12, 2));

        attractions = new ArrayList<>(Arrays.asList(
                new Attraction("北大红楼", 0, 1),
                new Attraction("北海公园", 2, 1),
                new Attraction("北京动物园", 4, 1),
                new Attraction("北京海洋馆", 4, 1),
                new Attraction("北京历代帝王庙博物馆", 0, 1),
                new Attraction("北京鲁迅博物馆", 0, 1),
                new Attraction("北京人民艺术剧院", 1, 1),
                new Attraction("北京天文馆", 4, 1),
                new Attraction("蔡元培故居", 0, 1),
                new Attraction("敕建火德真君庙", 1, 1),
                new Attraction("恭王府博物馆", 0, 1),
                new Attraction("鼓楼", 0, 1),
                new Attraction("故宫博物院", 0, 1),
                new Attraction("北京簋街", 3, 1),
                new Attraction("郭沫若故居", 0, 1),
                new Attraction("后海公园", 2, 1),
                new Attraction("后海酒吧一条街", 3, 1),
                new Attraction("护国寺街", 3, 1),
                new Attraction("景山公园", 2, 1),
                new Attraction("孔庙和国子监博物馆", 0, 1),
                new Attraction("老舍故居", 0, 1),
                new Attraction("茅盾故居", 0, 1),
                new Attraction("梅兰芳大剧院", 1, 1),
                new Attraction("南锣鼓巷", 3, 1),
                new Attraction("宋庆龄同志故居", 0, 1),
                new Attraction("王府井步行街", 3, 1),
                new Attraction("西什库天主堂", 1, 1),
                new Attraction("西直门天主堂", 1, 1),
                new Attraction("烟袋斜街", 3, 1),
                new Attraction("雍和宫", 0, 1),
                new Attraction("长安大戏院", 1, 1),
                new Attraction("中国美术馆", 1, 1),
                new Attraction("中山公园", 2, 1),
                new Attraction("白云观", 1, 2),
                new Attraction("北京大栅栏", 3, 2),
                new Attraction("北京古代建筑博物馆", 0, 2),
                new Attraction("北京欢乐谷", 4, 2),
                new Attraction("北京明城墙遗址公园", 0, 2),
                new Attraction("大观园", 2, 2),
                new Attraction("国家大剧院", 1, 2),
                new Attraction("国家自然博物馆", 1, 2),
                new Attraction("今日美术馆", 1, 2),
                new Attraction("李大钊故居", 0, 2),
                new Attraction("牛街", 3, 2),
                new Attraction("前门大街", 3, 2),
                new Attraction("首都博物馆", 1, 2),
                new Attraction("陶然亭公园", 2, 2)
                ));

        shoppingZones = new HashMap<>();
        shoppingZones.put(1, new String[]{"王府井大街", "东方新天地", "apm", "西单大悦城", "金融街购物中心", "凯德MALL·西直门"});
        shoppingZones.put(2, new String[]{"赛特购物中心", "北京市正阳市场", "新华联生活超市(昌盛便利店)(天桥南里店)"});
        shoppingZones.put(3, new String[]{"中关村欧美汇购物中心", "世纪金源购物中心西区", "世纪金源购物中心东区", "融科资讯中心商业"});
        shoppingZones.put(4, new String[]{"三里屯太古里", "燕莎友谊商城", "蓝色港湾", "颐堤港", "宜家家居(四元桥商场)", "欧陆时尚购物中心", "王府井奥莱·香江小镇"});
        shoppingZones.put(5, new String[]{"新世纪商城", "物美超市(昌平南环路店)", "悦荟", "阳光商厦(燕平路)", "金五星商城"});
        shoppingZones.put(6, new String[]{"万达广场", "星东天地生活购物广场", "京北大世界", "兴华商城", "世纪园商城"});
        shoppingZones.put(7, new String[]{"石景山万达广场", "大兴绿地缤纷城", "北京荟聚购物中心", "北京奥特莱斯（丰台）"});
        shoppingZones.put(8, new String[]{"永辉超市(北京门头沟区金融街融悦汇店)", "物美品超市(石门营店)", "广联超市(大灰厂路店)", "广联惠民超市(北宫店)", "惠好24h便利店融悦汇店"});
        shoppingZones.put(9, new String[]{"大运河市场", "鑫隆生活广场", "玫瑰坊商业中心", "北京华联BHG Mall(武夷店)", "贵友百货(通州店)", "阳光新生活广场(通州店)", "领展购物广场·京通"});
        shoppingZones.put(10, new String[]{"物美超市(榆垡店)", "吉象之家", "创鲜市界生活超市(榆垡旗舰店)"});
        shoppingZones.put(11, new String[]{"北京迎华顺达商贸中心", "百品尚购物中心", "延庆人民商场", "中踏广场"});
        shoppingZones.put(12, new String[]{"华冠欢乐城", "亿达·奥特莱斯", "蓝腾购物中心", "新时代冠华商场"});
    }

    public void answerQuestion(int questionIndex, Object selectedAnswers) {
        answers.set(questionIndex, selectedAnswers);
    }

    public Object getAnswer(int questionIndex) {
        return answers.get(questionIndex);
    }

    public String getTravelPlan() {
        StringBuilder plan = new StringBuilder();
        Random random = new Random(System.currentTimeMillis());

        int travelDays = getTravelDays();
        String travelPersonality = getTravelPersonality();
        plan.append("旅游人格: ").append(travelPersonality).append("\n\n");

        Set<String> allAttractions = getAllAttractions();  // 获取所有景点的列表
        Set<String> usedAttractions = new LinkedHashSet<>(); // 存储已经使用过的景点

        // 生成一个包含1到12的列表
        List<Integer> zones = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            zones = IntStream.rangeClosed(1, 12).boxed().collect(Collectors.toList());
        }
        // 打乱列表顺序
        Collections.shuffle(zones, random);

        for (int day = 1; day <= travelDays; day++) {
            int zone = zones.get((day / 2) % zones.size());

            plan.append("第").append(convertNumberToChinese(day)).append("天\n");


            plan.append("今天的推荐地在区域").append(zone).append("附近\n");

            plan.append("住: ");
            String[] selectedAccommodations = chooseAccommodations(zone);
            Set<String> accommodationSet = new LinkedHashSet<>();
            while (accommodationSet.size() < Math.min(2, selectedAccommodations.length)) {
                String accommodation = selectedAccommodations[random.nextInt(selectedAccommodations.length)];
                accommodationSet.add(accommodation);
            }
            plan.append(String.join("、", accommodationSet));
            plan.append(";\n");

            plan.append("游: ");
            String[] selectedAttractions = chooseAttraction(zone);
            List<String> selectedAttractionList = new ArrayList<>();
            Set<String> attractionSet = new LinkedHashSet<>();
            while (attractionSet.size() < Math.min(2, selectedAttractions.length)) {
                String attraction = selectedAttractions[random.nextInt(selectedAttractions.length)];
                if (usedAttractions.contains(attraction)) {
                    // 如果所选景点已经存在于之前的推荐中，则从总景点库中选择其他景点
                    attraction = getRandomElement(allAttractions);
                    while (usedAttractions.contains(attraction) && usedAttractions.size() < allAttractions.size()) {
                        attraction = getRandomElement(allAttractions);
                    }
                }
                attractionSet.add(attraction);
                selectedAttractionList.add(attraction);
                usedAttractions.add(attraction);
            }
            plan.append(String.join("、", attractionSet));
            plan.append(";\n");
            // 将List转换为String[]
            String[] s1 = selectedAttractionList.toArray(new String[0]);

            plan.append("吃: ");
            String[] selectedrestaurants = chooserestaurants(s1);
            Set<String> restaurantSet = new LinkedHashSet<>();
            while (restaurantSet.size() < Math.min(2, selectedrestaurants.length)) {
                String restaurant = selectedrestaurants[random.nextInt(selectedrestaurants.length)];
                restaurantSet.add(restaurant);
            }
            plan.append(String.join("、", restaurantSet));
            plan.append(";\n");

            plan.append("购: ");
            String[] selectedshops = chooseshopping(zone);
            Set<String> shopSet = new LinkedHashSet<>();
            while (shopSet.size() < Math.min(2, selectedshops.length)) {
                String shop = selectedshops[random.nextInt(selectedshops.length)];
                shopSet.add(shop);
            }
            plan.append(String.join("、", shopSet));
            plan.append(";\n");

            plan.append("\n");
        }

        return plan.toString();
    }

    private String convertNumberToChinese(int number) {
        String[] chineseNumbers = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
        if (number >= 1 && number <= 10) {
            return chineseNumbers[number - 1];
        } else {
            return String.valueOf(number); // 返回原数字字符串，如果不在范围内
        }
    }


    public Set<String> getAllAttractions() {
        Set<String> allAttractions = new LinkedHashSet<>();
        for (Attraction attraction : attractions) {
            allAttractions.add(attraction.getName()); // 提取景点名称并添加到 Set 中
        }
        return allAttractions;
    }

    private String getRandomElement(Set<String> set) {
        int index = new Random().nextInt(set.size());
        return new ArrayList<>(set).get(index);
    }

    private int getTravelDays() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("TravelPrefs", Context.MODE_PRIVATE);
        int defaultDays = 3; // 默认值为3
        return sharedPreferences.getInt("selectedDays", defaultDays);
    }

    //饭店
    private String[] chooserestaurants(String[] selectedAttractions) {
        Integer cuisineAnswer  = (Integer) getAnswer(2);
        Integer budgetAnswer = (Integer) getAnswer(3);

        String[] selectedRestaurants = filterRestaurants(selectedAttractions, cuisineAnswer, budgetAnswer);

        // 用于记录是否遍历了所有的budgetAnswer和cuisineAnswer组合
        Set<String> triedCombinations = new HashSet<>();

        // 如果没有找到餐馆，尝试更改 budgetAnswer 或 cuisineAnswer
        while (selectedRestaurants.length == 0 && triedCombinations.size() < 6) {
            // 记录当前组合
            triedCombinations.add(cuisineAnswer + "-" + budgetAnswer);

            // 尝试扩大预算范围或更改菜系类型
            if (budgetAnswer != null) {
                if (budgetAnswer == 0) {
                    budgetAnswer = 1;  // 更改为更广泛的范围
                } else if (budgetAnswer == 1) {
                    budgetAnswer = 2;  // 更改为下一个更广泛的范围
                } else {
                    budgetAnswer = 0;  // 重置为初始范围
                }
            }

            selectedRestaurants = filterRestaurants(selectedAttractions, cuisineAnswer, budgetAnswer);

            if (selectedRestaurants.length == 0) {
                // 尝试更改菜系类型
                if (cuisineAnswer != null) {
                    cuisineAnswer = (cuisineAnswer == 0) ? 1 : 0;
                }

                selectedRestaurants = filterRestaurants(selectedAttractions, cuisineAnswer, budgetAnswer);
            }

            // 防止陷入死循环，如果遍历了所有组合但仍然没有找到餐馆，跳出循环
            if (triedCombinations.contains(cuisineAnswer + "-" + budgetAnswer)) {
                break;
            }
        }

        return selectedRestaurants;
    }

    private String[] filterRestaurants(String[] selectedAttractions, Integer cuisineAnswer, Integer budgetAnswer) {
        String cuisineType = null;
        String priceRange = null;

        // Map the answers to actual cuisine type and price range
        if (cuisineAnswer != null) {
            switch (cuisineAnswer) {
                case 0:
                    cuisineType = "本地风味";
                    break;
                case 1:
                    cuisineType = "外地风味";
                    break;
            }
        }

        if (budgetAnswer != null) {
            switch (budgetAnswer) {
                case 0:
                    priceRange = "50元以下";
                    break;
                case 1:
                    priceRange = "50~200元";
                    break;
                case 2:
                    priceRange = "200元以上";
                    break;
            }
        }

        List<String> selectedRestaurantsList = new ArrayList<>();

        // Filter restaurants based on selected attractions, cuisine type, and price range
        for (Restaurant restaurant : restaurants) {
            if ((cuisineType == null || restaurant.cuisineType.equals(cuisineType)) &&
                    (priceRange == null || restaurant.priceRange.equals(priceRange))) {
                for (String attraction : selectedAttractions) {
                    if (Arrays.asList(restaurant.nearbyAttractions).contains(attraction)) {
                        selectedRestaurantsList.add(restaurant.name);
                        break;
                    }
                }
            }
        }

        return selectedRestaurantsList.toArray(new String[0]);
    }

    //住 酒店
    private String[] chooseAccommodations(int zone) {
        Integer preferredType = (Integer) getAnswer(4);
        Integer priceRange = (Integer) getAnswer(5);

        List<Hotel> filteredHotels = new ArrayList<>();
        int attempts = 0;
        final int MAX_ATTEMPTS = 12; // 设置最大尝试次数，避免无限循环

        do {
            filteredHotels.clear(); // 清空列表

            // 根据片区和问卷回答过滤酒店
            for (Hotel hotel : hotels) {
                if (hotel.zone == zone && (hotel.type == preferredType || hotel.type == modifyType(preferredType))) {
                    switch (priceRange) {
                        case 0:
                            if (hotel.priceLevel == 0 || hotel.priceLevel == modifyPriceRange(priceRange)) filteredHotels.add(hotel);
                            break;
                        case 1:
                            if (hotel.priceLevel == 1 || hotel.priceLevel == modifyPriceRange(priceRange)) filteredHotels.add(hotel);
                            break;
                        case 2:
                            if (hotel.priceLevel == 2 || hotel.priceLevel == modifyPriceRange(priceRange)) filteredHotels.add(hotel);
                            break;
                    }
                }
            }

            if (!filteredHotels.isEmpty()) {
                // 将 Hotel 对象转换为 String 数组（例如酒店名称）
                String[] hotelNames = new String[filteredHotels.size()];
                for (int i = 0; i < filteredHotels.size(); i++) {
                    hotelNames[i] = filteredHotels.get(i).getName(); // 假设 Hotel 类有 getName() 方法
                }
                return hotelNames;
            }

            // 修改类型或价位后再试
            if (attempts < MAX_ATTEMPTS / 2) {
                preferredType = modifyType(preferredType);
            } else {
                priceRange = modifyPriceRange(priceRange);
            }
            attempts++;
        } while (attempts < MAX_ATTEMPTS);

        // 如果仍然没有找到符合条件的酒店，返回提示信息
        return new String[]{"没有符合条件的酒店"};
    }

    // 修改类型的示例方法
    private int modifyType(int currentType) {
        // 假设有3种类型（0, 1, 2），这里可以根据实际情况调整
        return (currentType + 1) % 3;
    }

    // 修改价位的示例方法
    private int modifyPriceRange(int currentRange) {
        // 假设有3种价位（0, 1, 2），这里可以根据实际情况调整
        return (currentRange + 1) % 3;
    }

    //景点
    public String[] chooseAttraction(int zone) {
        Object attractionAnswer = getAnswer(6);
        List<String> selectedAttractionsList = new ArrayList<>();

        if (attractionAnswer instanceof Integer) {
            int selectedIndex = (Integer) attractionAnswer;
            String[] attractionsArray = getAttractionsArrayByIndex(selectedIndex,zone);
            selectedAttractionsList.addAll(Arrays.asList(attractionsArray));
        } else if (attractionAnswer instanceof List) {
            List<Integer> selectedIndices = (List<Integer>) attractionAnswer;
            for (int index : selectedIndices) {
                String[] attractionsArray = getAttractionsArrayByIndex(index,zone);
                selectedAttractionsList.addAll(Arrays.asList(attractionsArray));
            }
        }

        // If the list is empty, change the zone and try again
        Random random = new Random();
        while (selectedAttractionsList.isEmpty()) {
            zone = random.nextInt(12) + 1; // Change to a new random zone
            if (attractionAnswer instanceof Integer) {
                int selectedIndex = (Integer) attractionAnswer;
                String[] attractionsArray = getAttractionsArrayByIndex(selectedIndex, zone);
                selectedAttractionsList.addAll(Arrays.asList(attractionsArray));
            } else if (attractionAnswer instanceof List) {
                List<Integer> selectedIndices = (List<Integer>) attractionAnswer;
                for (int index : selectedIndices) {
                    String[] attractionsArray = getAttractionsArrayByIndex(index, zone);
                    selectedAttractionsList.addAll(Arrays.asList(attractionsArray));
                }
            }
        }

        return selectedAttractionsList.toArray(new String[0]);
    }

    private String[] getAttractionsArrayByIndex(int index,int zone) {
        List<String> filteredAttractions = new ArrayList<>();

        for (Attraction attraction : attractions) {
            if (attraction.zone == zone && attraction.category == index) {
                filteredAttractions.add(attraction.toString());
            }
        }

        return filteredAttractions.toArray(new String[0]);
    }

    //购物
    private String[] chooseshopping(int zone) {
        Integer shoppingTypeAnswer = (Integer) getAnswer(7);
        switch (shoppingTypeAnswer) {
            case 0:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    return shoppingZones.getOrDefault(zone, new String[]{});
                }
            case 1:
                return new String[]{"无"};
        }
        return new String[0];
    }

    //人格计算
    public String getTravelPersonality() {
        Object preferenceAnswers = answers.get(6); // 第7个问题是游玩偏好

        if (preferenceAnswers instanceof List) {
            List<Integer> selectedAnswers = (List<Integer>) preferenceAnswers;
            if (selectedAnswers.size() == 1) {
                int answer = selectedAnswers.get(0);
                if (answer == 0 || answer == 1) {
                    return "文化探访型游客";
                } else if (answer == 2) {
                    return "休闲放松型游客";
                } else if (answer == 3 || answer == 4) {
                    return "体验多元型游客";
                }
            } else if (selectedAnswers.size() == 2) {
                if (selectedAnswers.contains(0) && selectedAnswers.contains(1)) {
                    return "文化探访型游客";
                } else {
                    return "体验多元型游客";
                }
            } else if (selectedAnswers.size() >= 3) {
                return "全面探索型游客";
            }
        }

        return "未知类型";
    }
}