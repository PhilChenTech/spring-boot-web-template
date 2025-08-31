 
前言 
《Java 開發手冊》是 Java 社區愛好者的集體智慧結晶和經驗總結，經歷了多次大規模一線
實戰的檢驗及不斷完善，整理成冊后，眾多社區開發者踴躍參与打磨完善，系統化地整理成冊，當
前的最新版本是黃山版。現代軟件行業的高速發展對開發者的綜合素質要求越來越高，因為不僅是
編程知識點，其它維度的知識點也會影響到軟件的最終交付質量。比如：五花八門的錯誤碼會人為
地增加排查問題的難度；數據庫的表結構和索引設計缺陷帶來的系統架構缺陷或性能風險；工程結
構混亂導致後續項目維護艱難；沒有鑒權的漏洞代碼容易被黑客攻擊等。所以本手冊以 Java 開發
者為中心視角，劃分為編程規約、異常日誌、單元測試、安全規約、MySQL 數據庫、工程結構、
設計規約七個維度，再根據內容特徵，細分成若干二級子目錄。此外，依據約束力強弱及故障敏感
性，規約依次分為【強制】、【推薦】、【參考】三大類。在延伸的信息中，“說明”對規約做了適
當擴展和解釋；“正例”提倡什麼樣的編碼和實現方式；“反例”說明需要提防的雷區，以及真實
的錯誤案例。 
手冊的願景是碼出高效，碼出質量。現代軟件架構的複雜性需要協同開發完成，如何高效地
協同呢？無規矩不成方圓，無規範難以協同，比如，制定交通法規表面上是要限制行車權，實際上
是保障公眾的人身安全，試想如果沒有限速，沒有紅綠燈，誰還敢上路行駛？對軟件來說，適當的
規範和標準絕不是消滅代碼內容的創造性、優雅性，而是限制過度個性化，以一種普遍認可的統一
方式一起做事，提升協作效率，降低溝通成本。代碼的字里行間流淌的是軟件系統的血液，代碼質
量的提升是盡可能少踩坑，杜絕踩重複的坑，切實提升系統穩定性，碼出質量。 
2017 年杭州雲棲大會上發布了配套的 Java 開發規約 IDE 插件，下載量已達到 275 萬人次，
阿里雲效也集成了代碼規約掃描引擎。2018 年 9 月在雲棲廳發布 36 萬字的配套詳解圖書《碼出高
效》，秉持“圖勝於表，表勝於言”的理念，深入淺出地將計算機基礎、面向對象思想、數據結構
與集合、JVM 探源與內存分析、併發與多線程、單元測試等知識豐富立體地呈現出來。本書緊扣學
以致用、學以精進的目標，結合一線開發的實踐經驗和故障案例，與底層源碼解析融會貫通，娓娓
道來。《碼出高效》和《Java 開發手冊（第 2 版）》稿費所得收入均捐贈公益事情，希望用技術
情懷幫助到更多的人。
Java 開發手冊（黃山版） 
 
 
目錄 
一、編程規約 ................................................................................................................................................................................. 1 
(一) 命名風格 ........................................................................................................................................................................ 1 
(二) 常量定義 ........................................................................................................................................................................ 3 
(三) 代碼格式 ........................................................................................................................................................................ 4 
(四) OOP 規約 ...................................................................................................................................................................... 6 
(五) 日期時間 ........................................................................................................................................................................ 9 
(六) 集合處理 ...................................................................................................................................................................... 10 
(七) 併發處理 ...................................................................................................................................................................... 14 
(八) 控制語句 ...................................................................................................................................................................... 17 
(九) 註釋規約 ...................................................................................................................................................................... 20 
(十) 前後端規約 .................................................................................................................................................................. 21 
(十一) 其他 .......................................................................................................................................................................... 23 
二、異常日誌 ............................................................................................................................................................................... 24 
(一) 錯誤碼 .......................................................................................................................................................................... 24 
(二) 異常處理 ...................................................................................................................................................................... 25 
(三) 日誌規約 ...................................................................................................................................................................... 26 
三、單元測試 ............................................................................................................................................................................... 29 
四、安全規約 ............................................................................................................................................................................... 31 
五、MySQL 數據庫 .................................................................................................................................................................... 32 
(一) 建表規約 ...................................................................................................................................................................... 32 
(二) 索引規約 ...................................................................................................................................................................... 33 
(三) SQL 語句 ..................................................................................................................................................................... 34 
(四) ORM 映射 ................................................................................................................................................................... 35 
六、工程結構 ............................................................................................................................................................................... 37 
(一) 應用分層 ...................................................................................................................................................................... 37 
(二) 二方庫依賴 .................................................................................................................................................................. 38 
(三) 服務器 .......................................................................................................................................................................... 39 
七、設計規約 ............................................................................................................................................................................... 40 
附 1：版本歷史 ............................................................................................................................................................................ 42 
附 2：專有名詞解釋.................................................................................................................................................................... 44 
附 3：錯誤碼列表 ........................................................................................................................................................................ 45 
 
Java 開發手冊（黃山版） 
 
1/51 
 
 
一、編程規約 
(一) 命名風格 
1.【強制】所有編程相關的命名均不能以下劃線或美元符號開始，也不能以下劃線或美元符號結束。 
反例：_name / __name / $Object / name_ / name$ / Object$ 
2.【強制】所有編程相關的命名嚴禁使用拼音與英文混合的方式，更不允許直接使用中文的方式。 
說明：正確的英文拼寫和語法可以讓閱讀者易於理解，避免歧義。注意，即使純拼音命名方式也要避免採用。 
正例：ali / alibaba / taobao / kaikeba / aliyun / youku / hangzhou 等國際通用的名稱，可視同英文。 
反例：DaZhePromotion【打折】/ getPingfenByName()【評分】 / String fw【福娃】/ int 變量名 = 3 
3.【強制】代碼和註釋中都要避免使用任何人類語言中的種族歧視性或侮辱性詞語。 
正例：blockList / allowList / secondary 
反例：blackList / whiteList / slave / SB / WTF 
4.【強制】類名使用 UpperCamelCase 風格，以下情形例外：DO / PO / DTO / BO / VO / UID 等。 
正例：ForceCode / UserDO / HtmlDTO / XmlService / TcpUdpDeal / TaPromotion 
反例：forcecode / UserDo / HTMLDto / XMLService / TCPUDPDeal / TAPromotion 
5.【強制】方法名、參數名、成員變量、局部變量都統一使用 lowerCamelCase 風格。 
正例：localValue / getHttpMessage() / inputUserId 
6.【強制】常量命名應該全部大寫，單詞間用下劃線隔開，力求語義表達完整清楚，不要嫌名字長。 
正例：MAX_STOCK_COUNT / CACHE_EXPIRED_TIME 
反例：MAX_COUNT / EXPIRED_TIME 
7.【強制】抽象類命名使用 Abstract 或 Base 開頭；異常類命名使用 Exception 結尾，測試類命名以它要
測試的類的名稱開始，以 Test 結尾。 
8.【強制】類型與中括號緊挨相連來定義數組。 
正例：定義整形數組 int[] arrayDemo。 
反例：在 main 參數中，使用 String args[] 來定義。 
9.【強制】POJO 類中的任何布爾類型的變量，都不要加 is 前綴，否則部分框架解析會引起序列化錯誤。 
說明：本文 MySQL 規約中的建表約定第 1 條，表達是與否的變量採用 is_xxx 的命名方式，所以需要在<resultMap> 
設置從 is_xxx 到 xxx 的映射關係。 
反例：定義為布爾類型 Boolean isDeleted 的字段，它的 getter 方法也是 isDeleted()，部分框架在反向解析時，“誤以
為”對應的字段名稱是 deleted，導致字段獲取不到，得到意料之外的結果或拋出異常。 
10.【強制】包名統一使用小寫，點分隔符之間有且僅有一個自然語義的英語單詞。包名統一使用單數形
式，但是類名如果有複數含義，類名可以使用複數形式。 
正例：應用工具類包名為 com.alibaba.ei.kunlun.aap.util；類名為 MessageUtils（此規則參考 spring 的框架結構）。 
11.【強制】避免在子父類的成員變量之間、或者不同代碼塊的局部變量之間採用完全相同的命名，使可理
解性降低。 
版本號 
制定團隊 
更新日期 
備註 
1.7.1 
全球 Java 社區開發者 
2022.02.03 
黃山版，新增 11 條新規約。 
Java 開發手冊（黃山版） 
 
2/51 
說明：子類、父類成員變量名相同，即使是 public 也是能夠通過編譯，而局部變量在同一方法內的不同代碼塊中同名 
也是合法的，但是要避免使用。對於非 setter / getter 的參數名稱也要避免與成員變量名稱相同。 
反例： 
public class ConfusingName { 
protected int stock; 
protected String alibaba; 
// 非 setter/getter 的參數名稱，不允許與本類成員變量同名 
public void access(String alibaba) { 
if (condition) { 
final int money = 666; 
// ... 
} 
for (int i = 0; i < 10; i++) { 
// 在同一方法體中，不允許與其它代碼塊中的 money 命名相同 
final int money = 15978; 
// ... 
} 
} 
} 
class Son extends ConfusingName { 
// 不允許與父類的成員變量名稱相同 
private int stock; 
} 
12.【強制】杜絕完全不規範的英文縮寫，避免望文不知義。 
反例：AbstractClass“縮寫”成 AbsClass；condition“縮寫”成 condi；Function“縮寫”成 Fu，此類隨意縮寫 
嚴重降低了代碼的可閱讀性。 
13.【推薦】為了達到代碼自解釋的目標，任何自定義編程元素在命名時，使用完整的單詞組合來表達。 
正例：在 JDK 中，對某個對象引用的 volatile 字段進行原子更新的類名為 AtomicReferenceFieldUpdater。 
反例：常見的方法內變量為 int a; 的定義方式。 
14.【推薦】在常量與變量命名時，表示類型的名詞放在詞尾，以提升辨識度。 
正例：startTime / workQueue / nameList / TERMINATED_THREAD_COUNT 
反例：startedAt / QueueOfWork / listName / COUNT_TERMINATED_THREAD 
15.【推薦】如果模塊、接口、類、方法使用了設計模式，在命名時要體現出具體模式。 
說明：將設計模式體現在名字中，有利於閱讀者快速理解架構設計思想。
 
正例： public class OrderFactory; 
public class LoginProxy; 
public class ResourceObserver; 
16.【推薦】接口類中的方法和屬性不要加任何修飾符號（public 也不要加），保持代碼的簡潔性，並加上
有效的 Javadoc 註釋。盡量不要在接口裡定義常量，如果一定要定義，最好確定該常量與接口的方法
相關，並且是整個應用的基礎常量。 
正例：接口方法簽名 void commit(); 
接口基礎常量 String COMPANY = "alibaba"; 
反例：接口方法定義 public abstract void commit(); 
說明：JDK8 中接口允許有默認實現，那麼這個 default 方法，是對所有實現類都有價值的默認實現。 
Java 開發手冊（黃山版） 
 
3/51 
17.接口和實現類的命名有兩套規則： 
1）【強制】對於 Service 和 DAO 類，基於 SOA 的理念，暴露出來的服務一定是接口，內部的實現類用 Impl 的後綴 
與接口區別。 
正例：CacheServiceImpl 實現 CacheService 接口。 
2）【推薦】如果是形容能力的接口名稱，取對應的形容詞為接口名（通常是 –able 結尾的形容詞）。 
正例：AbstractTranslator 實現 Translatable。 
18.【參考】枚舉類名帶上 Enum 後綴，枚舉成員名稱需要全大寫，單詞間用下劃線隔開。 
說明：枚舉其實就是特殊的常量類，且構造方法被默認強制是私有。 
正例：枚舉名字為 ProcessStatusEnum 的成員名稱：SUCCESS / UNKNOWN_REASON 
19.【參考】各層命名規約： 
A）Service / DAO 層方法命名規約： 
1）獲取單個對象的方法用 get 做前綴。 
2）獲取多個對象的方法用 list 做前綴，複數結尾，如：listObjects 
3）獲取統計值的方法用 count 做前綴。 
4）插入的方法用 save / insert 做前綴。 
5）刪除的方法用 remove / delete 做前綴。 
6）修改的方法用 update 做前綴。 
B）領域模型命名規約： 
1）數據對象：xxxDO，xxx 即為數據表名。 
2）數據傳輸對象：xxxDTO，xxx 為業務領域相關的名稱。 
3）展示對象：xxxVO，xxx 一般為網頁名稱。 
4）POJO 是 DO / DTO / BO / VO 的統稱，禁止命名成 xxxPOJO。 
 
(二) 常量定義 
1.【強制】不允許任何魔法值（即未經預先定義的常量）直接出現在代碼中。 
反例： 
//  開發者 A 定義了緩存的 key。 
String key = "Id#taobao_" + tradeId; 
cache.put(key, value); 
// 開發者 B 使用緩存時直接複製少了下劃線，即 key 是"Id#taobao" + tradeId，導致出現故障。 
String key = "Id#taobao" + tradeId; 
cache.get(key); 
2.【強制】long 或 Long 賦值時，數值后使用大寫 L，不能是小寫 l，小寫容易跟数字混淆，造成誤解。 
說明：public static final Long NUM = 2l; 寫的是数字的 21，還是 Long 型的 2？ 
3.【強制】浮點數類型的數值後綴統一為大寫的 D 或 F。 
正例：public static final double HEIGHT = 175.5D; 
   public static final float WEIGHT = 150.3F; 
4.【推薦】不要使用一個常量類維護所有常量，要按常量功能進行歸類，分開維護。 
說明：大而全的常量類，雜亂無章，使用查找功能才能定位到要修改的常量，不利於理解，也不利於維護。 
正例：緩存相關常量放在類 CacheConsts 下；系統配置相關常量放在類 SystemConfigConsts 下。 
5.【推薦】常量的復用層次有五層：跨應用共享常量、應用內共享常量、子工程內共享常量、包內共享常
量、類內共享常量。 
1）跨應用共享常量：放置在二方庫中，通常是 client.jar 中的 constant 目錄下。 
2）應用內共享常量：放置在一方庫中，通常是子模塊中的 constant 目錄下。 
Java 開發手冊（黃山版） 
 
4/51 
反例：易懂常量也要統一定義成應用內共享常量，兩個程序員在兩個類中分別定義了表示“是”的常量： 
類 A 中：public static final String YES = "yes"; 
類 B 中：public static final String YES = "y"; 
A.YES.equals(B.YES)，預期是 true，但實際返回為 false，導致線上問題。 
3）子工程內部共享常量：即在當前子工程的 constant 目錄下。 
4）包內共享常量：即在當前包下單獨的 constant 目錄下。 
5）類內共享常量：直接在類內部 private static final 定義。 
6.【推薦】如果變量值僅在一個固定範圍內變化用 enum 類型來定義。 
說明：如果存在名稱之外的延伸屬性應使用 enum 類型，下面正例中的数字就是延伸信息，表示一年中的第幾個季節。 
正例： 
public enum SeasonEnum { 
SPRING(1), SUMMER(2), AUTUMN(3), WINTER(4); 
private int seq; 
SeasonEnum(int seq) { 
this.seq = seq; 
} 
public int getSeq() { 
return seq; 
} 
} 
 
(三) 代碼格式 
1.【強制】如果大括號內為空，簡潔地寫成{}即可，大括號中間無需換行和空格；如果是非空代碼塊，則： 
1）左大括號前不換行。 
2）左大括號后換行。 
3）右大括號前換行。 
4）右大括號后還有 else 等代碼則不換行；表示終止的右大括號后必須換行。 
2.【強制】左小括號和右邊相鄰字符之間不需要空格；右小括號和左邊相鄰字符之間也不需要空格；而左大
括號前需要加空格。詳見第 5 條下方正例提示。 
反例：if(空格 a == b 空格) 
3.【強制】if / for / while / switch / do 等保留字與左右括號之間都必須加空格。 
4.【強制】任何二目、三目運算符的左右兩邊都需要加一個空格。 
說明：包括賦值運算符 =、邏輯運算符 &&、加減乘除符號等。 
5.【強制】採用 4 個空格縮進，禁止使用 Tab 字符。 
說明：如使用 Tab 縮進，必須設置 1 個 Tab 為 4 個空格。IDEA 設置 Tab 為 4 個空格時，請勿勾選 Use tab character；
而在 Eclipse 中，找到 tab policy 設置為 Spaces only，Tab size：4，最後必須勾選 insert spaces for tabs 
正例：（涉及上述中的 1-5 點） 
public static void main(String[] args) { 
// 縮進 4 個空格 
String say = "hello"; 
// 運算符的左右必須有一個空格 
int flag = 0; 
// 關鍵詞 if 與括號之間必須有一個空格，括號內的 f 與左括號，0 與右括號不需要空格 
if (flag == 0) { 
System.out.println(say); 
Java 開發手冊（黃山版） 
 
5/51 
} 
// 左大括號前加空格且不換行；左大括號后換行 
if (flag == 1) { 
System.out.println("world"); 
// 右大括號前換行，右大括號後有 else，不用換行 
} else { 
System.out.println("ok"); 
// 在右大括號后直接結束，則必須換行 
} 
} 
6.【強制】註釋的雙斜線與註釋內容之間有且僅有一個空格。 
正例： 
// 這是示例註釋，請注意在雙斜線之後有一個空格 
String commentString = new String("demo"); 
7.【強制】在進行類型強制轉換時，右括號與強制轉換值之間不需要任何空格隔開。 
正例： 
double first = 3.2D; 
int second = (int)first + 2; 
8.【強制】單行字符數限制不超過 120 個，超出需要換行，換行時遵循如下原則： 
1）第二行相對第一行縮進 4 個空格，從第三行開始，不再繼續縮進，參考示例。 
2）運算符與下文一起換行。 
3）方法調用的點符號與下文一起換行。 
4）方法調用中的多個參數需要換行時，在逗號後進行。 
5）在括號前不要換行，見反例。 
正例： 
StringBuilder builder = new StringBuilder(); 
// 超過 120 個字符的情況下，換行縮進 4 個空格，並且方法前的點號一起換行 
builder.append("yang").append("hao")... 
.append("chen")... 
.append("chen")... 
.append("chen"); 
反例： 
StringBuilder builder = new StringBuilder(); 
// 超過 120 個字符的情況下，不要在括號前換行 
builder.append("you").append("are")...append 
("lucky"); 
// 參數很多的方法調用可能超過 120 個字符，逗號后才是換行處 
method(args1, args2, args3, ... 
, argsX); 
9.【強制】方法參數在定義和傳入時，多個參數逗號後面必須加空格。 
正例：下例中實參的 args1 逗號後邊必須要有一個空格。 
method(args1, args2, args3); 
10.【強制】IDE 的 text file encoding 設置為 UTF-8；IDE 中文件的換行符使用 Unix 格式，不要使用
Windows 格式。 
11.【推薦】單個方法的總行數不超過 80 行。 
說明：除註釋之外的方法簽名、左右大括號、方法內代碼、空行、回車及任何不可見字符的總行數不超過 80 行。 
Java 開發手冊（黃山版） 
 
6/51 
正例：代碼邏輯分清紅花和綠恭弘=叶 恭弘，個性和共性，綠恭弘=叶 恭弘邏輯單獨出來成為額外方法，使主幹代碼更加晰；共性邏輯抽取 
成為共性方法，便於復用和維護。 
12.【推薦】沒有必要增加若干空格來使變量的賦值等號與上一行對應位置的等號對齊。 
正例： 
int one = 1; 
long two = 2L; 
float three = 3F; 
StringBuilder builder = new StringBuilder(); 
說明：增加 builder 這個變量，如果需要對齊，則給 one、two、three 都要增加幾個空格，在變量比較多的情況下，是 
非常累贅的事情。 
13.【推薦】不同邏輯、不同語義、不同業務的代碼之間插入一個空行，分隔開來以提升可讀性。 
說明：任何情形，沒有必要插入多個空行進行隔開。 
 
(四) OOP 規約 
1.【強制】避免通過一個類的對象引用訪問此類的靜態變量或靜態方法，無謂增加編譯器解析成本，直接用
類名來訪問即可。 
2.【強制】所有的覆寫方法，必須加 @Override 註解。 
說明：getObject() 與 get0bject() 的問題。一個是字母的 O，一個是数字的 0，加 @Override 可以準確判斷是否覆蓋
成功。另外，如果在抽象類中對方法簽名進行修改，其實現類會馬上編譯報錯。 
3.【強制】相同參數類型，相同業務含義，才可以使用的可變參數，參數類型避免定義為 Object。 
說明：可變參數必須放置在參數列表的最後。（建議開發者盡量不用可變參數編程） 
正例：public List<User> listUsers(String type, Long... ids) {...} 
4.【強制】外部正在調用的接口或者二方庫依賴的接口，不允許修改方法簽名，避免對接口調用方產生影
響。接口過時必須加 @Deprecated 註解，並清晰地說明採用的新接口或者新服務是什麼。 
5.【強制】不能使用過時的類或方法。 
說明：java.net.URLDecoder 中的方法 decode(String encodeStr) 這個方法已經過時，應該使用雙參數
decode(String source, String encode)。接口提供方既然明確是過時接口，那麼有義務同時提供新的接口；作為調用
方來說，有義務去考證過時方法的新實現是什麼。 
6.【強制】Object 的 equals 方法容易拋空指針異常，應使用常量或確定有值的對象來調用 equals。 
正例："test".equals(param); 
反例：param.equals("test"); 
說明：推薦使用 JDK7 引入的工具類 java.util.Objects#equals(Object a, Object b) 
7.【強制】所有整型包裝類對象之間值的比較，全部使用 equals 方法比較。 
說明：對於 Integer var = ? 在 -128 至 127 之間的賦值，Integer 對象是在 IntegerCache.cache 產生，會復用已有對
象，這個區間內的 Integer 值可以直接使用 == 進行判斷，但是這個區間之外的所有數據，都會在堆上產生，並不會復
用已有對象，這是一個大坑，推薦使用 equals 方法進行判斷。 
8.【強制】任何貨幣金額，均以最小貨幣單位且為整型類型進行存儲。 
9.【強制】浮點數之間的等值判斷，基本數據類型不能使用 == 進行比較，包裝數據類型不能使用 equals
進行判斷。 
說明：浮點數採用“尾數+階碼”的編碼方式，類似於科學計數法的“有效数字+指數”的表示方式。二進制無法精確表
示大部分的十進制小數，具體原理參考《碼出高效》。 
反例： 
Java 開發手冊（黃山版） 
 
7/51 
float a = 1.0F - 0.9F; 
float b = 0.9F - 0.8F; 
if (a == b) { 
// 預期進入此代碼塊，執行其它業務邏輯 
// 但事實上 a == b 的結果為 false 
} 
Float x = Float.valueOf(a); 
Float y = Float.valueOf(b); 
if (x.equals(y)) { 
// 預期進入此代碼塊，執行其它業務邏輯 
// 但事實上 equals 的結果為 false 
} 
正例： 
(1)指定一個誤差範圍，兩個浮點數的差值在此範圍之內，則認為是相等的。 
float a = 1.0F - 0.9F; 
float b = 0.9F - 0.8F; 
float diff = 1e-6F; 
if (Math.abs(a - b) < diff) { 
System.out.println("true"); 
} 
(2)使用 BigDecimal 來定義值，再進行浮點數的運算操作。 
BigDecimal a = new BigDecimal("1.0"); 
BigDecimal b = new BigDecimal("0.9"); 
BigDecimal c = new BigDecimal("0.8"); 
BigDecimal x = a.subtract(b); 
BigDecimal y = b.subtract(c); 
 
if (x.compareTo(y) == 0) { 
System.out.println("true"); 
} 
10.【強制】BigDecimal 的等值比較應使用 compareTo() 方法，而不是 equals() 方法。 
 說明：equals() 方法會比較值和精度（1.0 與 1.00 返回結果為 false），而 compareTo() 則會忽略精度。 
11.【強制】定義數據對象 DO 類時，屬性類型要與數據庫字段類型相匹配。 
 正例：數據庫字段的 bigint 必須與類屬性的 Long 類型相對應。 
反例：某業務的數據庫表 id 字段定義類型為 bigint unsigned，實際類對象屬性為 Integer，隨着 id 越來越大， 
超過 Integer 的表示範圍而溢出成為負數，此時數據庫 id 不支持存入負數拋出異常產生線上故障。 
12.【強制】禁止使用構造方法 BigDecimal(double) 的方式把 double 值轉化為 BigDecimal 對象。 
說明：BigDecimal(double) 存在精度損失風險，在精確計算或值比較的場景中可能會導致業務邏輯異常。 如： 
BigDecimal g = new BigDecimal(0.1F)；實際的存儲值為：0.100000001490116119384765625 
正例：優先推薦入參為 String 的構造方法，或使用 BigDecimal 的 valueOf 方法，此方法內部其實執行了 Double 的
toString，而 Double 的 toString 按 double 的實際能表達的精度對尾數進行了截斷。 
BigDecimal recommend1 = new BigDecimal("0.1"); 
BigDecimal recommend2 = BigDecimal.valueOf(0.1); 
13.關於基本數據類型與包裝數據類型的使用標準如下： 
1）【強制】所有的 POJO 類屬性必須使用包裝數據類型。 
2）【強制】RPC 方法的返回值和參數必須使用包裝數據類型。 
3）【推薦】所有的局部變量使用基本數據類型。 
Java 開發手冊（黃山版） 
 
8/51 
說明：POJO 類屬性沒有初值是提醒使用者在需要使用時，必須自己顯式地進行賦值，任何 NPE 問題，或者入庫檢查， 
都由使用者來保證。 
正例：數據庫的查詢結果可能是 null，因為自動拆箱，用基本數據類型接收有 NPE 風險。 
反例：某業務的交易報表上显示成交總額漲跌情況，即正負 x%，x 為基本數據類型，調用的 RPC 服務，調用不成功時， 
返回的是默認值，頁面显示為 0%，這是不合理的，應該显示成中劃線-。所以包裝數據類型的 null 值，能夠表示額外的 
信息，如：遠程調用失敗，異常退出。 
14.【強制】定義 DO / PO / DTO / VO 等 POJO 類時，不要設定任何屬性默認值。 
反例：某業務的 DO 的 createTime 默認值為 new Date()；但是這個屬性在數據提取時並沒有置入具體值，在更新其
它字段時又附帶更新了此字段，導致創建時間被修改成當前時間。 
15.【強制】序列化類新增屬性時，請不要修改 serialVersionUID 字段，避免反序列失敗；如果完全不兼
容升級，避免反序列化混亂，那麼請修改 serialVersionUID 值。 
說明：注意 serialVersionUID 不一致會拋出序列化運行時異常。 
16.【強制】構造方法裏面禁止加入任何業務邏輯，如果有初始化邏輯，請放在 init 方法中。 
17.【強制】POJO 類必須寫 toString 方法。使用 IDE 中的工具 source > generate toString 時，如果繼
承了另一個 POJO 類，注意在前面加一下 super.toString()。 
說明：在方法執行拋出異常時，可以直接調用 POJO 的 toString() 方法打印其屬性值，便於排查問題。 
18.【強制】禁止在 POJO 類中，同時存在對應屬性 xxx 的 isXxx() 和 getXxx() 方法。 
說明：框架在調用屬性 xxx 的提取方法時，並不能確定哪個方法一定是被優先調用到，神坑之一。 
19.【推薦】使用索引訪問用 String 的 split 方法得到的數組時，需做最後一個分隔符後有無內容的檢查，
否則會有拋 IndexOutOfBoundsException 的風險。 
說明： 
String str = "a,b,c,,"; 
String[] ary = str.split(","); 
// 預期大於 3，結果等於 3 
System.out.println(ary.length); 
20.【推薦】當一個類有多個構造方法，或者多個同名方法，這些方法應該按順序放置在一起，便於閱讀，
此條規則優先於下一條。 
正例： 
   public int method(int param); 
   protected double method(int param1, int param2); 
   private void method(); 
21.【推薦】類內方法定義的順序依次是：公有方法或保護方法 > 私有方法 > getter / setter 方法。 
說明：公有方法是類的調用者和維護者最關心的方法，首屏展示最好；保護方法雖然只是子類關心，也可能是“模板設
計模式”下的核心方法；而私有方法外部一般不需要特別關心，是一個黑盒實現；因為承載的信息價值較低，所有
Service 和 DAO 的 getter / setter 方法放在類體最後。 
22.【推薦】setter 方法中，參數名稱與類成員變量名稱一致，this.成員名=參數名。在 getter / setter 方
法中，不要增加業務邏輯，增加排查問題的難度。 
反例： 
public Integer getData() { 
if (condition) { 
return this.data + 100; 
} else { 
return this.data - 100; 
Java 開發手冊（黃山版） 
 
9/51 
} 
} 
23.【推薦】循環體內，字符串的連接方式，使用 StringBuilder 的 append 方法進行擴展。 
 反例： 
String str = "start"; 
for (int i = 0; i < 100; i++) { 
str = str + "hello"; 
} 
說明：反編譯出的字節碼文件显示每次循環都會 new 出一個 StringBuilder 對象，然後進行 append 操作，最後通過
toString() 返回 String 對象，造成內存資源浪費。 
24.【推薦】final 可以聲明類、成員變量、方法、以及本地變量，下列情況使用 final 關鍵字： 
1）不允許被繼承的類，如：String 類。 
2）不允許修改引用的域對象，如：POJO 類的域變量。 
3）不允許被覆寫的方法，如：POJO 類的 setter 方法。 
4）不允許運行過程中重新賦值的局部變量。 
5）避免上下文重複使用一個變量，使用 final 關鍵字可以強制重新定義一個變量，方便更好地進行重構。 
25.【推薦】慎用 Object 的 clone 方法來拷貝對象。 
 說明：對象 clone 方法默認是淺拷貝，若想實現深拷貝需覆寫 clone 方法實現域對象的深度遍歷式拷貝。 
26.【推薦】類成員與方法訪問控制從嚴： 
1）如果不允許外部直接通過 new 來創建對象，那麼構造方法必須是 private。 
2）工具類不允許有 public 或 default 構造方法。 
3）類非 static 成員變量並且與子類共享，必須是 protected。 
4）類非 static 成員變量並且僅在本類使用，必須是 private。 
5）類 static 成員變量如果僅在本類使用，必須是 private。 
6）若是 static 成員變量，考慮是否為 final。 
7）類成員方法只供類內部調用，必須是 private。 
8）類成員方法只對繼承類公開，那麼限製為 protected。 
說明：任何類、方法、參數、變量，嚴控訪問範圍。過於寬泛的訪問範圍，不利於模塊解耦。思考：如果是一個
private 的方法，想刪除就刪除，可是一個 public 的 service 成員方法或成員變量，刪除一下，不得手心冒點汗嗎？ 
變量像自己的小孩，盡量在自己的視線內，變量作用域太大，無限制的到處跑，那麼你會擔心的。 
 
(五) 日期時間 
1.【強制】日期格式化時，傳入 pattern 中表示年份統一使用小寫的 y。 
說明：日期格式化時，yyyy 表示當天所在的年，而大寫的 YYYY 代表是 week in which year（JDK7 之後引入的概念），
意思是當天所在的周屬於的年份，一周從周日開始，周六結束，只要本周跨年，返回的 YYYY 就是下一年。 
正例：表示日期和時間的格式如下所示： 
new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") 
反例：某程序員因使用 YYYY/MM/dd 進行日期格式化，2017/12/31 執行結果為 2018/12/31，造成線上故障。 
2.【強制】在日期格式中分清楚大寫的 M 和小寫的 m，大寫的 H 和小寫的 h 分別指代的意義。 
說明：日期格式中的這兩對字母表意如下： 
1）表示月份是大寫的 M 
2）表示分鐘則是小寫的 m 
3）24 小時制的是大寫的 H 
4）12 小時制的則是小寫的 h 
Java 開發手冊（黃山版） 
 
10/51 
3.【強制】獲取當前毫秒數：System.currentTimeMillis()；而不是 new Date().getTime()。 
說明：獲取納秒級時間，則使用 System.nanoTime 的方式。在 JDK8 中，針對統計時間等場景，推薦使用 Instant 類。 
4.【強制】不允許在程序任何地方中使用：1）java.sql.Date  2）java.sql.Time  3）java.sql.Timestamp。 
說明：第 1 個不記錄時間，getHours() 拋出異常；第 2 個不記錄日期，getYear() 拋出異常；第 3 個在構造方法
super((time / 1000) * 1000)，在 Timestamp 屬性 fastTime 和 nanos 分別存儲秒和納秒信息。 
反例：java.util.Date.after(Date) 進行時間比較時，當入參是 java.sql.Timestamp 時，會觸發 JDK BUG（JDK9 已修
復），可能導致比較時的意外結果。 
5.【強制】禁止在程序中寫死一年為 365 天，避免在公曆閏年時出現日期轉換錯誤或程序邏輯錯誤。 
正例： 
// 獲取今年的天數 
int daysOfThisYear = LocalDate.now().lengthOfYear(); 
// 獲取指定某年的天數 
LocalDate.of(2011, 1, 1).lengthOfYear(); 
反例： 
// 第一種情況：在閏年 366 天時，出現數組越界異常 
int[] dayArray = new int[365]; 
// 第二種情況：一年有效期的會員制，2020 年 1 月 26 日註冊，硬編碼 365 返回的卻是 2021 年 1 月 25 日 
Calendar calendar = Calendar.getInstance(); 
calendar.set(2020, 1, 26); 
calendar.add(Calendar.DATE, 365); 
6.【推薦】避免公曆閏年 2 月問題。閏年的 2 月份有 29 天，一年後的那一天不可能是 2 月 29 日。 
7.【推薦】使用枚舉值來指代月份。如果使用数字，注意 Date，Calendar 等日期相關類的月份 month 取
值範圍從 0 到 11 之間。 
說明：參考 JDK 原生註釋，Month value is 0-based. e.g., 0 for January. 
正例：Calendar.JANUARY，Calendar.FEBRUARY，Calendar.MARCH 等來指代相應月份來進行傳參或比較。 
 
(六) 集合處理 
1.【強制】關於 hashCode 和 equals 的處理，遵循如下規則： 
1）只要覆寫 equals，就必須覆寫 hashCode。 
2）因為 Set 存儲的是不重複的對象，依據 hashCode 和 equals 進行判斷，所以 Set 存儲的對象必須覆寫這兩種方法。 
3）如果自定義對象作為 Map 的鍵，那麼必須覆寫 hashCode 和 equals。 
說明：String 因為覆寫了 hashCode 和 equals 方法，所以可以愉快地將 String 對象作為 key 來使用。 
2.【強制】判斷所有集合內部的元素是否為空，使用 isEmpty() 方法，而不是 size() == 0 的方式。 
說明：在某些集合中，前者的時間複雜度為 O(1)，而且可讀性更好。 
正例： 
Map<String, Object> map = new HashMap<>(16); 
if (map.isEmpty()) { 
System.out.println("no element in this map."); 
} 
3.【強制】在使用 java.util.stream.Collectors 類的 toMap() 方法轉為 Map 集合時，一定要使用參數類型
為 BinaryOperator，參數名為 mergeFunction 的方法，否則當出現相同 key 時會拋出
IllegalStateException 異常。 
說明：參數 mergeFunction 的作用是當出現 key 重複時，自定義對 value 的處理策略。 
正例： 
Java 開發手冊（黃山版） 
 
11/51 
List<Pair<String, Double>> pairArrayList = new ArrayList<>(3); 
pairArrayList.add(new Pair<>("version", 12.10)); 
pairArrayList.add(new Pair<>("version", 12.19)); 
pairArrayList.add(new Pair<>("version", 6.28)); 
 
// 生成的 map 集合中只有一個鍵值對：{version=6.28} 
Map<String, Double> map = pairArrayList.stream() 
.collect(Collectors.toMap(Pair::getKey, Pair::getValue, (v1, v2) -> v2)); 
反例： 
String[] departments = new String[]{"RDC", "RDC", "KKB"}; 
// 拋出 IllegalStateException 異常 
Map<Integer, String> map = Arrays.stream(departments) 
.collect(Collectors.toMap(String::hashCode, str -> str)); 
4.【強制】在使用 java.util.stream.Collectors 類的 toMap() 方法轉為 Map 集合時，一定要注意當 value
為 null 時會拋 NPE 異常。 
說明：在 java.util.HashMap 的 merge 方法里會進行如下的判斷： 
if (value == null || remappingFunction == null) 
throw new NullPointerException(); 
反例： 
List<Pair<String, Double>> pairArrayList = new ArrayList<>(2); 
pairArrayList.add(new Pair<>("version1", 8.3)); 
pairArrayList.add(new Pair<>("version2", null)); 
 
// 拋出 NullPointerException 異常 
Map<String, Double> map = pairArrayList.stream() 
.collect(Collectors.toMap(Pair::getKey, Pair::getValue, (v1, v2) -> v2)); 
5.【強制】ArrayList 的 subList 結果不可強轉成 ArrayList，否則會拋出 ClassCastException 異常：
java.util.RandomAccessSubList cannot be cast to java.util.ArrayList。 
說明：subList() 返回的是 ArrayList 的內部類 SubList，並不是 ArrayList 本身，而是 ArrayList 的一個視圖，對於
SubList 的所有操作最終會反映到原列表上。 
6.【強制】使用 Map 的方法 keySet() / values() / entrySet() 返回集合對象時，不可以對其進行添加元素
操作，否則會拋出 UnsupportedOperationException 異常。 
7.【強制】Collections 類返回的對象，如：emptyList() / singletonList() 等都是 immutable list，不可
對其進行添加或者刪除元素的操作。 
 
反例：如果查詢無結果，返回 Collections.emptyList() 空集合對象，調用方一旦在返回的集合中進行了添加元素的操
作，就會觸發 UnsupportedOperationException 異常。 
8.【強制】在 subList 場景中，高度注意對父集合元素的增加或刪除，均會導致子列表的遍歷、增加、刪
除產生 ConcurrentModificationException 異常。 
說明：抽查表明，90% 的程序員對此知識點都有錯誤的認知。 
9.【強制】使用集合轉數組的方法，必須使用集合的 toArray(T[] array)，傳入的是類型完全一致、長度為
0 的空數組。 
反例：直接使用 toArray 無參方法存在問題，此方法返回值只能是 Object[]類，若強轉其它類型數組將出現
ClassCastException 錯誤。 
正例： 
List<String> list = new ArrayList<>(2); 
Java 開發手冊（黃山版） 
 
12/51 
list.add("guan"); 
list.add("bao"); 
String[] array = list.toArray(new String[0]); 
說明：使用 toArray 帶參方法，數組空間大小的 length： 
1）等於 0，動態創建與 size 相同的數組，性能最好。 
2）大於 0 但小於 size，重新創建大小等於 size 的數組，增加 GC 負擔。 
3）等於 size，在高併發情況下，數組創建完成之後，size 正在變大的情況下，負面影響與 2 相同。 
4）大於 size，空間浪費，且在 size 處插入 null 值，存在 NPE 隱患。 
10.【強制】使用 Collection 接口任何實現類的 addAll() 方法時，要對輸入的集合參數進行 NPE 判斷。 
說明：在 ArrayList#addAll 方法的第一行代碼即 Object[] a = c.toArray()；其中 c 為輸入集合參數，如果為 null， 
則直接拋出異常。 
11.【強制】使用工具類 Arrays.asList() 把數組轉換成集合時，不能使用其修改集合相關的方法，它的 add 
/ remove / clear 方法會拋出 UnsupportedOperationException 異常。 
說明：asList 的返回對象是一個 Arrays 內部類，並沒有實現集合的修改方法。Arrays.asList 體現的是適配器模式，只
是轉換接口，後台的數據仍是數組。 
String[] str = new String[]{ "yang", "guan", "bao" }; 
List list = Arrays.asList(str); 
 第一種情況：list.add("yangguanbao"); 運行時異常。 
 第二種情況：str[0] = "change"; list 中的元素也會隨之修改，反之亦然。 
12.【強制】泛型通配符<? extends T>來接收返回的數據，此寫法的泛型集合不能使用 add 方法， 
而<? super T>不能使用 get 方法，兩者在接口調用賦值的場景中容易出錯。 
 
說明：擴展說一下 PECS(Producer Extends Consumer Super) 原則，即頻繁往外讀取內容的，適合用 
<? extends T>，經常往裡插入的，適合用<? super T> 
13.【強制】在無泛型限制定義的集合賦值給泛型限制的集合時，在使用集合元素時，需要進行
instanceof 判斷，避免拋出 ClassCastException 異常。 
說明：畢竟泛型是在 JDK5 后才出現，考慮到向前兼容，編譯器是允許非泛型集合與泛型集合互相賦值。 
反例： 
List<String> generics = null; 
List notGenerics = new ArrayList(10); 
notGenerics.add(new Object()); 
notGenerics.add(new Integer(1)); 
generics = notGenerics; 
// 此處拋出 ClassCastException 異常 
String string = generics.get(0); 
14.【強制】不要在 foreach 循環里進行元素的 remove / add 操作。remove 元素請使用 iterator 方式， 
如果併發操作，需要對 iterator 對象加鎖。 
正例： 
List<String> list = new ArrayList<>(); 
list.add("1"); 
list.add("2"); 
Iterator<String> iterator = list.iterator(); 
while (iterator.hasNext()) { 
String item = iterator.next(); 
if (刪除元素的條件) { 
iterator.remove(); 
} 
Java 開發手冊（黃山版） 
 
13/51 
} 
反例： 
for (String item : list) { 
if ("1".equals(item)) { 
list.remove(item); 
} 
} 
 說明：反例中的執行結果肯定會出乎大家的意料，那麼試一下把“1”換成“2”會是同樣的結果嗎？ 
15.【強制】在 JDK7 版本及以上，Comparator 實現類要滿足如下三個條件，不然 Arrays.sort，
Collections.sort 會拋 IllegalArgumentException 異常。 
說明：三個條件如下 
1）x，y 的比較結果和 y，x 的比較結果相反。 
2）x > y，y > z，則 x > z。 
3）x = y，則 x，z 比較結果和 y，z 比較結果相同。 
反例：下例中沒有處理相等的情況，交換兩個對象判斷結果並不互反，不符合第一個條件，在實際使用中可能會出現異
常。 
new Comparator<Student>() { 
@Override 
public int compare(Student o1, Student o2) { 
return o1.getId() > o2.getId() ? 1 : -1; 
} 
}; 
16.【推薦】泛型集合使用時，在 JDK7 及以上，使用 diamond 語法或全省略。 
說明：菱形泛型，即 diamond，直接使用<>來指代前邊已經指定的類型。 
正例： 
// diamond 方式，即<> 
HashMap<String, String> userCache = new HashMap<>(16); 
// 全省略方式 
ArrayList<User> users = new ArrayList(10); 
17.【推薦】集合初始化時，指定集合初始值大小。 
說明：HashMap 使用構造方法 HashMap(int initialCapacity) 進行初始化時，如果暫時無法確定集合大小，那麼指
定默認值（16）即可。 
正例：initialCapacity = (需要存儲的元素個數 / 負載因子) + 1。注意負載因子（即 loaderfactor）默認為 0.75，如果
暫時無法確定初始值大小，請設置為 16（即默認值）。 
反例：HashMap 需要放置 1024 個元素，由於沒有設置容量初始大小，隨着元素增加而被迫不斷擴容，resize() 方法
總共會調用 8 次，反覆重建哈希表和數據遷移。當放置的集合元素個數達千萬級時會影響程序性能。 
18.【推薦】使用 entrySet 遍歷 Map 類集合 KV，而不是 keySet 方式進行遍歷。 
說明：keySet 其實是遍歷了 2 次，一次是轉為 Iterator 對象，另一次是從 hashMap 中取出 key 所對應的 value。而
entrySet 只是遍歷了一次就把 key 和 value 都放到了 entry 中，效率更高。如果是 JDK8，使用 Map.forEach 方法。 
正例：values() 返回的是 V 值集合，是一個 list 集合對象；keySet() 返回的是 K 值集合，是一個 Set 集合對象；
entrySet() 返回的是 K-V 值組合的 Set 集合。 
19.【推薦】高度注意 Map 類集合 K / V 能不能存儲 null 值的情況，如下錶格： 
集合類 
Key 
Value 
Super 
說明 
Hashtable 
不允許為 null 
不允許為 null 
Dictionary 
線程安全 
Java 開發手冊（黃山版） 
 
14/51 
TreeMap 
不允許為 null 
允許為 null 
AbstractMap 
線程不安全 
ConcurrentHashMap 
不允許為 null 
不允許為 null 
AbstractMap 
鎖分段技術（JDK8:CAS） 
HashMap 
允許為 null 
允許為 null 
AbstractMap 
線程不安全 
反例：由於 HashMap 的干擾，很多人認為 ConcurrentHashMap 是可以置入 null 值，而事實上，存儲 null 值時會拋
出 NPE 異常。 
20.【參考】合理利用好集合的有序性（sort）和穩定性（order），避免集合的無序性（unsort）和不穩定
性（unorder）帶來的負面影響。 
說明：有序性是指遍歷的結果是按某種比較規則依次排列的，穩定性指集合每次遍歷的元素次序是一定的。如：
ArrayList 是 order / unsort；HashMap 是 unorder / unsort；TreeSet 是 order / sort。 
21.【參考】利用 Set 元素唯一的特性，可以快速對一個集合進行去重操作，避免使用 List 的
contains() 進行遍歷去重或者判斷包含操作。 
(七) 併發處理 
1.【強制】獲取單例對象需要保證線程安全，其中的方法也要保證線程安全。 
說明：資源驅動類、工具類、單例工廠類都需要注意。 
2.【強制】創建線程或線程池時請指定有意義的線程名稱，方便出錯時回溯。 
正例：自定義線程工廠，並且根據外部特徵進行分組，比如，來自同一機房的調用，把機房編號賦值給 
whatFeatureOfGroup： 
public class UserThreadFactory implements ThreadFactory { 
private final String namePrefix; 
private final AtomicInteger nextId = new AtomicInteger(1); 
// 定義線程組名稱，在利用 jstack 來排查問題時，非常有幫助 
UserThreadFactory(String whatFeatureOfGroup) { 
namePrefix = "FromUserThreadFactory's" + whatFeatureOfGroup + "-Worker-"; 
} 
@Override 
public Thread newThread(Runnable task) { 
String name = namePrefix + nextId.getAndIncrement(); 
Thread thread = new Thread(null, task, name, 0, false); 
System.out.println(thread.getName()); 
return thread; 
} 
} 
3.【強制】線程資源必須通過線程池提供，不允許在應用中自行顯式創建線程。 
說明：線程池的好處是減少在創建和銷毀線程上所消耗的時間以及系統資源的開銷，解決資源不足的問題。如果不使用
線程池，有可能造成系統創建大量同類線程而導致消耗完內存或者“過度切換”的問題。 
4.【強制】線程池不允許使用 Executors 去創建，而是通過 ThreadPoolExecutor 的方式，這樣的處理方
式讓寫的同學更加明確線程池的運行規則，規避資源耗盡的風險。 
說明：Executors 返回的線程池對象的弊端如下： 
1）FixedThreadPool 和 SingleThreadPool： 
允許的請求隊列長度為 Integer.MAX_VALUE，可能會堆積大量的請求，從而導致 OOM。 
2）CachedThreadPool： 
允許的創建線程數量為 Integer.MAX_VALUE，可能會創建大量的線程，從而導致 OOM。 
3）ScheduledThreadPool： 
Java 開發手冊（黃山版） 
 
15/51 
允許的請求隊列長度為 Integer.MAX_VALUE，可能會堆積大量的請求，從而導致 OOM。 
5.【強制】SimpleDateFormat 是線程不安全的類，一般不要定義為 static 變量，如果定義為 static，必須
加鎖，或者使用 DateUtils 工具類。 
正例：注意線程安全，使用 DateUtils。亦推薦如下處理： 
private static final ThreadLocal<DateFormat> dateStyle = new ThreadLocal<DateFormat>() { 
@Override 
protected DateFormat initialValue() { 
return new SimpleDateFormat("yyyy-MM-dd"); 
} 
}; 
說明：如果是 JDK8 的應用，可以使用 Instant 代替 Date，LocalDateTime 代替 Calendar，DateTimeFormatter 代替
SimpleDateFormat，官方給出的解釋：simple beautiful strong immutable thread-safe。 
6.【強制】必須回收自定義的 ThreadLocal 變量記錄的當前線程的值，尤其在線程池場景下，線程經常會
被複用，如果不清理自定義的 ThreadLocal 變量，可能會影響後續業務邏輯和造成內存泄露等問題。
盡量在代碼中使用 try-finally 塊進行回收。 
正例： 
objectThreadLocal.set(userInfo); 
try { 
// ... 
} finally { 
objectThreadLocal.remove(); 
} 
7.【強制】高併發時，同步調用應該去考量鎖的性能損耗。能用無鎖數據結構，就不要用鎖；能鎖區塊，就
不要鎖整個方法體；能用對象鎖，就不要用類鎖。 
說明：盡可能使加鎖的代碼塊工作量盡可能的小，避免在鎖代碼塊中調用 RPC 方法。 
8.【強制】對多個資源、數據庫表、對象同時加鎖時，需要保持一致的加鎖順序，否則可能會造成死鎖。 
說明：線程一需要對錶 A、B、C 依次全部加鎖后才可以進行更新操作，那麼線程二的加鎖順序也必須是 A、B、C，否則可
能出現死鎖。 
9.【強制】在使用阻塞等待獲取鎖的方式中，必須在 try 代碼塊之外，並且在加鎖方法與 try 代碼塊之間沒
有任何可能拋出異常的方法調用，避免加鎖成功后，在 finally 中無法解鎖。 
說明一：在 lock 方法與 try 代碼塊之間的方法調用拋出異常，無法解鎖，造成其它線程無法成功獲取鎖。 
說明二：如果 lock 方法在 try 代碼塊之內，可能由於其它方法拋出異常，導致在 finally 代碼塊中，unlock 對未加鎖的對
象解鎖，它會調用 AQS 的 tryRelease 方法（取決於具體實現類），拋出 IllegalMonitorStateException 異常。 
說明三：在 Lock 對象的 lock 方法實現中可能拋出 unchecked 異常，產生的後果與說明二相同。 
正例： 
Lock lock = new XxxLock(); 
// ... 
lock.lock(); 
try { 
doSomething(); 
doOthers(); 
} finally { 
lock.unlock(); 
} 
反例： 
Lock lock = new XxxLock(); 
Java 開發手冊（黃山版） 
 
16/51 
// ... 
try { 
// 如果此處拋出異常，則直接執行 finally 代碼塊 
doSomething(); 
// 無論加鎖是否成功，finally 代碼塊都會執行 
lock.lock(); 
doOthers(); 
} finally { 
lock.unlock(); 
} 
10.【強制】在使用嘗試機制來獲取鎖的方式中，進入業務代碼塊之前，必須先判斷當前線程是否持有鎖。
鎖的釋放規則與鎖的阻塞等待方式相同。 
說明：Lock 對象的 unlock 方法在執行時，它會調用 AQS 的 tryRelease 方法（取決於具體實現類），如果當前線程不
持有鎖，則拋出 IllegalMonitorStateException 異常。 
 正例： 
Lock lock = new XxxLock(); 
// ... 
boolean isLocked = lock.tryLock(); 
if (isLocked) { 
try { 
doSomething(); 
doOthers(); 
} finally { 
lock.unlock(); 
} 
} 
11.【強制】併發修改同一記錄時，避免更新丟失，需要加鎖。要麼在應用層加鎖，要麼在緩存加鎖，要麼
在數據庫層使用樂觀鎖，使用 version 作為更新依據。 
  說明：如果每次訪問衝突概率小於 20%，推薦使用樂觀鎖，否則使用悲觀鎖。樂觀鎖的重試次數不得小於 3 次。 
12.【強制】多線程并行處理定時任務時，Timer 運行多個 TimeTask 時，只要其中之一沒有捕獲拋出的異
常，其它任務便會自動終止運行，使用 ScheduledExecutorService 則沒有這個問題。 
13.【推薦】資金相關的金融敏感信息，使用悲觀鎖策略。 
說明：樂觀鎖在獲得鎖的同時已經完成了更新操作，校驗邏輯容易出現漏洞，另外，樂觀鎖對衝突的解決策略有較複雜
的要求，處理不當容易造成系統壓力或數據異常，所以資金相關的金融敏感信息不建議使用樂觀鎖更新。 
正例：悲觀鎖遵循一鎖二判三更新四釋放的原則。 
14.【推薦】使用 CountDownLatch 進行異步轉同步操作，每個線程退出前必須調用 countDown 方法，線
程執行代碼注意 catch 異常，確保 countDown 方法被執行到，避免主線程無法執行至 await 方法，
直到超時才返回結果。 
 說明：注意，子線程拋出異常堆棧，不能在主線程 try-catch 到。 
15.【推薦】避免 Random 實例被多線程使用，雖然共享該實例是線程安全的，但會因競爭同一 seed 導致
的性能下降。 
 說明：Random 實例包括 java.util.Random 的實例或者 Math.random() 的方式。 
 正例：在 JDK7 之後，可以直接使用 API ThreadLocalRandom，而在 JDK7 之前，需要編碼保證每個線程持有一個
單獨的 Random 實例。 
Java 開發手冊（黃山版） 
 
17/51 
16.【推薦】通過雙重檢查鎖（double-checked locking），實現延遲初始化需要將目標屬性聲明為 
 volatile 型，（比如修改 helper 的屬性聲明為 private volatile Helper helper = null;）。 
正例： 
public class LazyInitDemo { 
private volatile Helper helper = null; 
public Helper getHelper() { 
if (helper == null) { 
synchronized(this) { 
if (helper == null) { 
helper = new Helper(); 
} 
} 
} 
return helper; 
} 
// other methods and fields... 
} 
17.【參考】volatile 解決多線程內存不可見問題對於一寫多讀，是可以解決變量同步問題，但是如果多
寫，同樣無法解決線程安全問題。 
說明：如果是 count++操作，使用如下類實現： 
AtomicInteger count = new AtomicInteger(); 
count.addAndGet(1); 
如果是 JDK8，推薦使用 LongAdder 對象，比 AtomicLong 性能更好（減少樂觀鎖的重試次數）。 
18.【參考】HashMap 在容量不夠進行 resize 時由於高併發可能出現死鏈，導致 CPU 飆升，在開發過程
中注意規避此風險。 
19.【參考】ThreadLocal 對象使用 static 修飾，ThreadLocal 無法解決共享對象的更新問題。 
說明：這個變量是針對一個線程內所有操作共享的，所以設置為靜態變量，所有此類實例共享此靜態變量，也就是說在
類第一次被使用時裝載，只分配一塊存儲空間，所有此類的對象（只要是這個線程內定義的）都可以操控這個變量。 
 
(八) 控制語句 
1.【強制】在一個 switch 塊內，每個 case 要麼通過 continue / break / return 等來終止，要麼註釋說明
程序將繼續執行到哪一個 case 為止；在一個 switch 塊內，都必須包含一個 default 語句並且放在最
后，即使它什麼代碼也沒有。 
說明：注意 break 是退出 switch 語句塊，而 return 是退出方法體。 
2.【強制】當 switch 括號內的變量類型為 String 並且此變量為外部參數時，必須先進行 null 判斷。 
反例：如下的代碼輸出是什麼？ 
public class SwitchString { 
public static void main(String[] args) { 
method(null); 
} 
public static void method(String param) { 
switch (param) { 
// 肯定不是進入這裏 
case "sth": 
Java 開發手冊（黃山版） 
 
18/51 
System.out.println("it's sth"); 
break; 
// 也不是進入這裏 
case "null": 
System.out.println("it's null"); 
break; 
// 也不是進入這裏 
default: 
System.out.println("default"); 
} 
} 
} 
3.【強制】在 if / else / for / while / do 語句中必須使用大括號。 
反例： if (condition) statements; 
說明：即使只有一行代碼，也要採用大括號的編碼方式。 
4.【強制】三目運算符 condition ? 表達式 1：表達式 2 中，高度注意表達式 1 和 2 在類型對齊時，可能
拋出因自動拆箱導致的 NPE 異常。 
說明：以下兩種場景會觸發類型對齊的拆箱操作： 
1）表達式 1 或 表達式 2 的值只要有一個是原始類型。 
2）表達式 1 或 表達式 2 的值的類型不一致，會強制拆箱升級成表示範圍更大的那個類型。 
反例： 
Integer a = 1; 
Integer b = 2; 
Integer c = null; 
Boolean flag = false; 
//  a*b 的結果是 int 類型，那麼 c 會強制拆箱成 int 類型，拋出 NPE 異常 
Integer result = (flag ? a * b : c); 
5.【強制】在高併發場景中，避免使用“等於”判斷作為中斷或退出的條件。 
說明：如果併發控制沒有處理好，容易產生等值判斷被“擊穿”的情況，使用大於或小於的區間判斷條件來代替。 
反例：判斷剩餘獎品數量等於 0 時，終止發放獎品，但因為併發處理錯誤導致獎品數量瞬間變成了負數，這樣的話， 
活動無法終止。 
6.【推薦】當方法的代碼總行數超過 10 行時，return / throw 等中斷邏輯的右大括號后需要加一個空行。 
說明：這樣做邏輯清晰，有利於代碼閱讀時重點關注。 
7.【推薦】表達異常的分支時，少用 if-else 方式，這種方式可以改寫成： 
if (condition) { 
... 
return obj; 
} 
// 接着寫 else 的業務邏輯代碼; 
說明：如果非使用 if()...else if()...else...方式表達邏輯，避免後續代碼維護困難，請勿超過 3 層。 
正例：超過 3 層的 if-else 的邏輯判斷代碼可以使用衛語句、策略模式、狀態模式等來實現，其中衛語句示例如下： 
public void findBoyfriend(Man man) { 
if (man.isUgly()) { 
System.out.println("本姑娘是外貌協會的資深會員"); 
return; 
} 
Java 開發手冊（黃山版） 
 
19/51 
if (man.isPoor()) { 
System.out.println("貧賤夫妻百事哀"); 
return; 
} 
if (man.isBadTemper()) { 
System.out.println("銀河有多遠，你就給我滾多遠"); 
return; 
} 
System.out.println("可以先交往一段時間看看"); 
} 
8.【推薦】除常用方法（如 getXxx / isXxx）等外不要在條件判斷中執行其它複雜的語句，將複雜邏輯判
斷的結果賦值給一個有意義的布爾變量名，以提高可讀性。 
說明：很多 if 語句內的邏輯表達式相當複雜，與、或、取反混合運算，甚至各種方法縱深調用，理解成本非常高。如果賦
值一個非常好理解的布爾變量名字，則是件令人爽心悅目的事情。 
正例： 
// 偽代碼如下 
final boolean existed = (file.open(fileName, "w") != null) && (...) || (...); 
if (existed) { 
... 
} 
反例： 
public final void acquire(long arg) { 
if (!tryAcquire(arg) && acquireQueued(addWaiter(Node.EXCLUSIVE), arg)) { 
selfInterrupt(); 
} 
 
} 
9.【推薦】不要在其它表達式（尤其是條件表達式）中，插入賦值語句。 
說明：賦值點類似於人體的穴位，對於代碼的理解至關重要，所以賦值語句需要清晰地單獨成為一行。 
反例： 
public Lock getLock(boolean fair) { 
// 算術表達式中出現賦值操作，容易忽略 count 值已經被改變 
threshold = (count = Integer.MAX_VALUE) - 1; 
// 條件表達式中出現賦值操作，容易誤認為是 sync == fair 
return (sync = fair) ? new FairSync() : new NonfairSync(); 
} 
10.【推薦】循環體中的語句要考量性能，以下操作盡量移至循環體外處理，如定義對象、變量、獲取數據
庫連接，進行不必要的 try-catch 操作（這個 try-catch 是否可以移至循環體外）。 
11.【推薦】避免採用取反邏輯運算符。 
說明：取反邏輯不利於快速理解，並且取反邏輯寫法一般都存在對應的正向邏輯寫法。 
正例：使用 if(x < 628) 來表達 x 小於 628。 
反例：使用 if(!(x >= 628)) 來表達 x 小於 628。 
12.【推薦】公開接口需要進行入參保護，尤其是批量操作的接口。 
反例：某業務系統，提供一個用戶批量查詢的接口，API 文檔上有說最多查多少個，但接口實現上沒做任何保護，導致
調用方傳了一個 1000 的用戶 id 數組過來后，查詢信息后，內存爆了。 
13.【參考】下列情形，需要進行參數校驗： 
Java 開發手冊（黃山版） 
 
20/51 
1）調用頻次低的方法。 
2）執行時間開銷很大的方法。此情形中，參數校驗時間幾乎可以忽略不計，但如果因為參數錯誤導致中間執行回
退，或者錯誤，那得不償失。 
3）需要極高穩定性和可用性的方法。 
4）對外提供的開放接口，不管是 RPC / API / HTTP 接口。 
5）敏感權限入口。 
14.【參考】下列情形，不需要進行參數校驗： 
1）極有可能被循環調用的方法。但在方法說明裡必須註明外部參數檢查。 
2）底層調用頻度比較高的方法。畢竟是像純凈水過濾的最後一道，參數錯誤不太可能到底層才會暴露問題。一般 DAO
層與 Service 層都在同一個應用中，部署在同一台服務器中，所以 DAO 的參數校驗，可以省略。 
3）被聲明成 private 只會被自己代碼所調用的方法，如果能夠確定調用方法的代碼傳入參數已經做過檢查或者肯定不
會有問題，此時可以不校驗參數。 
 
(九) 註釋規約 
1.【強制】類、類屬性、類方法的註釋必須使用 Javadoc 規範，使用 /** 內容 */ 格式，不得使用 // xxx
方式。 
說明：在 IDE 編輯窗口中，Javadoc 方式會提示相關註釋，生成 Javadoc 可以正確輸出相應註釋；在 IDE 中，工程調用
方法時，不進入方法即可懸浮提示方法、參數、返回值的意義，提高閱讀效率。 
2.【強制】所有的抽象方法（包括接口中的方法）必須要用 Javadoc 註釋、除了返回值、參數異常說明
外，還必須指出該方法做什麼事情，實現什麼功能。 
說明：對子類的實現要求，或者調用注意事項，請一併說明。 
3.【強制】所有的類都必須添加創建者和創建日期。 
說明：在設置模板時，注意 IDEA 的@author 為`${USER}`，而 eclipse 的@author 為`${user}`，大小寫有區別，而日期
的設置統一為 yyyy/MM/dd 的格式。 
正例： 
/** 
 
* 
 
* @author yangguanbao 
* @date 2021/11/26 
 
* 
**/ 
4.【強制】方法內部單行註釋，在被註釋語句上方另起一行，使用 // 註釋。方法內部多行註釋使用 /*  */ 
註釋，注意與代碼對齊。 
5.【強制】所有的枚舉類型字段必須要有註釋，說明每個數據項的用途。 
6.【推薦】與其用半吊子英文來註釋，不如用中文註釋說清楚。專有名詞與關鍵字保持英文原文即可。 
反例：“TCP 連接超時”解釋成“傳輸控制協議連接超時”，理解反而費腦筋。 
7.【推薦】代碼修改的同時，註釋也要進行相應的修改，尤其是參數、返回值、異常、核心邏輯等。 
說明：代碼與註釋更新不同步，就像公路網與導航軟件更新不同步一樣，如果導航軟件嚴重滯后，就失去了導航的意義。 
8.【推薦】在類中刪除未使用的任何字段和方法、內部類；在方法中刪除未使用的參數聲明與內部變量。 
9.【參考】謹慎註釋掉代碼。在上方詳細說明，而不是簡單地註釋掉。如果無用，則刪除。 
說明：代碼被註釋掉有兩種可能性：1）後續會恢復此段代碼邏輯。2）永久不用。前者如果沒有備註信息，難以知曉注
釋動機。後者建議直接刪掉即可，假如需要查閱歷史代碼，登錄代碼倉庫即可。 
Java 開發手冊（黃山版） 
 
21/51 
10.【參考】對於註釋的要求：第一、能夠準確反映設計思想和代碼邏輯；第二、能夠描述業務含義，使別
的程序員能夠迅速了解到代碼背後的信息。完全沒有註釋的大段代碼對於閱讀者形同天書，註釋是給
自己看的，即使隔很長時間，也能清晰理解當時的思路；註釋也是給繼任者看的，使其能夠快速接替
自己的工作。 
11.【參考】好的命名、代碼結構是自解釋的，註釋力求精簡準確、表達到位。避免出現註釋的另一個極
端：過多過濫的註釋，代碼的邏輯一旦修改，修改註釋又是相當大的負擔。 
反例： 
// put elephant into fridge 
put(elephant, fridge); 
方法名 put，加上兩個有意義的變量名稱 elephant 和 fridge，已經說明了這是在干什麼，語義清晰的代碼不需要額外
的註釋。 
12.【參考】特殊註釋標記，請註明標記人與標記時間。注意及時處理這些標記，通過標記掃描，經常清理
此類標記。線上故障有時候就是來源於這些標記處的代碼。 
1）待辦事宜（TODO）：（標記人，標記時間，[預計處理時間]）表示需要實現，但目前還未實現的功能。這實際上是
一個 Javadoc 的標籤，目前的 Javadoc 還沒有實現，但已經被廣泛使用。只能應用於類，接口和方法（因為它是一個
Javadoc 標籤）。 
2）錯誤，不能工作（FIXME）：（標記人，標記時間，[預計處理時間]）在註釋中用 FIXME 標記某代碼是錯誤的，而
且不能工作，需要及時糾正的情況。 
 
(十) 前後端規約 
1.【強制】前後端交互的 API，需要明確協議、域名、路徑、請求方法、請求內容、狀態碼、響應體。 
說明： 
1）協議：生產環境必須使用 HTTPS。 
2）路徑：每一個 API 需對應一個路徑，表示 API 具體的請求地址： 
a）代表一種資源，只能為名詞，推薦使用複數，不能為動詞，請求方法已經表達動作意義。 
b）URL 路徑不能使用大寫，單詞如果需要分隔，統一使用下劃線。 
c）路徑禁止攜帶表示請求內容類型的後綴，比如".json"，".xml"，通過 accept 頭表達即可。 
3）請求方法：對具體操作的定義，常見的請求方法如下： 
a）GET：從服務器取出資源。 
b）POST：在服務器新建一個資源。 
c）PUT：在服務器更新資源。 
d）DELETE：從服務器刪除資源。 
4）請求內容：URL 帶的參數必須無敏感信息或符合安全要求；body 裡帶參數時必須設置 Content-Type。 
5）響應體：響應體 body 可放置多種數據類型，由 Content-Type 頭來確定。 
2.【強制】前後端數據列表相關的接口返回，如果為空，則返回空數組[]或空集合{}。 
說明：此條約定有利於數據層面上的協作更加高效，減少前端很多瑣碎的 null 判斷。 
3.【強制】服務端發生錯誤時，返回給前端的響應信息必須包含 HTTP 狀態碼，errorCode、
errorMessage、用戶提示信息四個部分。 
說明：四個部分的涉眾對象分別是瀏覽器、前端開發、錯誤排查人員、用戶。其中輸出給用戶的提示信息要求：簡短清
晰、提示友好，引導用戶進行下一步操作或解釋錯誤原因，提示信息可以包括錯誤原因、上下文環境、推薦操作等。
errorCode：參考
。errorMessage：簡要描述後端出錯原因，便於錯誤排查人員快速定位問題，注意不要包含敏
感數據信息。 
正例：常見的 HTTP 狀態碼如下 
1）200 OK：表明該請求被成功地完成，所請求的資源發送到客戶端。 
Java 開發手冊（黃山版） 
 
22/51 
2）401 Unauthorized：請求要求身份驗證，常見對於需要登錄而用戶未登錄的情況。 
3）403 Forbidden：服務器拒絕請求，常見於機密信息或複製其它登錄用戶鏈接訪問服務器的情況。 
4）404 NotFound：服務器無法取得所請求的網頁，請求資源不存在。 
5）500 InternalServerError：服務器內部錯誤。 
4.【強制】在前後端交互的 JSON 格式數據中，所有的 key 必須為小寫字母開始的 lowerCamelCase
風格，符合英文表達習慣，且表意完整。 
正例：errorCode / errorMessage / assetStatus / menuList / orderList / configFlag 
反例：ERRORCODE / ERROR_CODE / error_message / error-message / errormessage  
5.【強制】errorMessage 是前後端錯誤追蹤機制的體現，可以在前端輸出到 type="hidden" 文字類控
件中，或者用戶端的日誌中，幫助我們快速地定位出問題。 
6.【強制】對於需要使用超大整數的場景，服務端一律使用 String 字符串類型返回，禁止使用 Long 類型。 
說明：Java 服務端如果直接返回 Long 整型數據給前端，Javascript 會自動轉換為 Number 類型（注：此類型為雙精度浮
點數，表示原理與取值範圍等同於 Java 中的 Double）。Long 類型能表示的最大值是 263-1，在取值範圍之內，超過 253
（9007199254740992）的數值轉化為 Javascript 的 Number 時，有些數值會產生精度損失。擴展說明，在 Long 取值范
圍內，任何 2 的指數次的整數都是絕對不會存在精度損失的，所以說精度損失是一個概率問題。若浮點數尾數位與指數位
空間不限，則可以精確表示任何整數，但很不幸，雙精度浮點數的尾數位只有 52 位。 
反例：通常在訂單號或交易號大於等於 16 位，大概率會出現前後端訂單數據不一致的情況。 
比如，後端傳輸的 "orderId"：362909601374617692，前端拿到的值卻是：362909601374617660 
7.【強制】HTTP 請求通過 URL 傳遞參數時，不能超過 2048 字節。 
說明：不同瀏覽器對於 URL 的最大長度限制略有不同，並且對超出最大長度的處理邏輯也有差異，2048 字節是取所
有瀏覽器的最小值。 
反例：某業務將退貨的商品 id 列表放在 URL 中作為參數傳遞，當一次退貨商品數量過多時，URL 參數超長，傳遞到後端的
參數被截斷，導致部分商品未能正確退貨。 
8.【強制】HTTP 請求通過 body 傳遞內容時，必須控制長度，超出最大長度后，後端解析會出錯。 
說明：nginx 默認限制是 1MB，tomcat 默認限製為 2MB，當確實有業務需要傳較大內容時，可以調大服務器端的限制。 
9.【強制】在翻頁場景中，用戶輸入參數的小於 1，則前端返回第一頁參數給後端；後端發現用戶輸入的
參數大於總頁數，直接返回最後一頁。 
10.【強制】服務器內部重定向必須使用 forward；外部重定向地址必須使用 URL 統一代理模塊生成，否
則會因線上採用 HTTPS 協議而導致瀏覽器提示“不安全”，並且還會帶來 URL 維護不一致的問題。 
11.【推薦】服務器返回信息必須被標記是否可以緩存，如果緩存，客戶端可能會重用之前的請求結果。 
說明：緩存有利於減少交互次數，減少交互的平均延遲。 
正例：http1.1 中，s-maxage 告訴服務器進行緩存，時間單位為秒，用法如下， 
response.setHeader("Cache-Control", "s-maxage=" + cacheSeconds); 
12.【推薦】服務端返回的數據，使用 JSON 格式而非 XML。 
說明：儘管 HTTP 支持使用不同的輸出格式，例如純文本，JSON，CSV，XML，RSS 甚至 HTML。如果我們使用的面
向用戶的服務，應該選擇 JSON 作為通信中使用的標準數據交換格式，包括請求和響應。此外，application/JSON 是
一種通用的 MIME 類型，具有實用、精簡、易讀的特點。 
13.【推薦】前後端的時間格式統一為"yyyy-MM-dd HH:mm:ss"，統一為 GMT。 
14.【參考】在接口路徑中不要加入版本號，版本控制在 HTTP 頭信息中體現，有利於向前兼容。 
 
說明：當用戶在低版本與高版本之間反覆切換工作時，會導致遷移複雜度升高，存在數據錯亂風險。 
 
Java 開發手冊（黃山版） 
 
23/51 
(十一) 其他 
1.【強制】在使用正則表達式時，利用好其預編譯功能，可以有效加快正則匹配速度。 
說明：不要在方法體內定義：Pattern pattern = Pattern.compile("規則"); 
2.【強制】避免用 ApacheBeanutils 進行屬性的 copy。 
說明：ApacheBeanUtils 性能較差，可以使用其他方案比如 SpringBeanUtils，CglibBeanCopier，注意均是淺拷貝。 
3.【強制】velocity 調用 POJO 類的屬性時，直接使用屬性名取值即可，模板引擎會自動按規範調用 POJO 
的 getXxx()，如果是 boolean 基本數據類型變量（boolean 命名不需要加 is 前綴），會自動調 isXxx() 
方法。 
說明：注意如果是 Boolean 包裝類對象，優先調用 getXxx() 的方法。 
4.【強制】後台輸送給頁面的變量必須加 $!{var} ——中間的感嘆號。 
說明：如果 var 等於 null 或者不存在，那麼 ${var} 會直接显示在頁面上。 
5.【強制】注意 Math.random() 這個方法返回是 double 類型，注意取值的範圍 0 ≤ x < 1（能夠
取到零值，注意除零異常），如果想獲取整數類型的隨機數，不要將 x 放大 10 的若干倍然後取
整，直接使用 Random 對象的 nextInt 或者 nextLong 方法。 
6.【強制】枚舉 enum（括號內）的屬性字段必須是私有且不可變。 
7.【推薦】不要在視圖模板中加入任何複雜的邏輯運算。 
說明：根據 MVC 理論，視圖的職責是展示，不要搶模型和控制器的活。 
8.【推薦】任何數據結構的構造或初始化，都應指定大小，避免數據結構無限增長吃光內存。 
9.【推薦】及時清理不再使用的代碼段或配置信息。 
說明：對於垃圾代碼或過時配置，堅決清理乾淨，避免程序過度臃腫，代碼冗餘。 
正例：對於暫時被註釋掉，後續可能恢復使用的代碼片斷，在註釋代碼上方，統一規定使用三個斜杠(///)
來說明註釋掉代碼的理由： 
  
public static void hello() { 
/// 業務方通知活動暫停 
// Business business = new Business(); 
// business.active(); 
System.out.println("it's finished"); 
} 
Java 開發手冊（黃山版） 
 
24/51 
二、異常日誌 
(一) 錯誤碼 
1.【強制】錯誤碼的制定原則：快速溯源、溝通標準化。 
說明：錯誤碼想得過於完美和複雜，就像康熙字典的生僻字一樣，用詞似乎精準，但是字典不容易隨身攜帶且簡單易懂。 
正例：錯誤碼回答的問題是誰的錯？錯在哪？ 
 1）錯誤碼必須能夠快速知曉錯誤來源，可快速判斷是誰的問題。 
 2）錯誤碼必須能夠進行清晰地比對（代碼中容易 equals）。 
 3）錯誤碼有利於團隊快速對錯誤原因達到一致認知。 
2.【強制】錯誤碼不體現版本號和錯誤等級信息。 
說明：錯誤碼以不斷追加的方式進行兼容。錯誤等級由日誌和錯誤碼本身的釋義來決定。 
3.【強制】全部正常，但不得不填充錯誤碼時返回五個零：00000。 
4.【強制】錯誤碼為字符串類型，共 5 位，分成兩個部分：錯誤產生來源+四位数字編號。 
說明：錯誤產生來源分為 A/B/C，A 表示錯誤來源於用戶，比如參數錯誤，用戶安裝版本過低，用戶支付超時等問題；
B 表示錯誤來源於當前系統，往往是業務邏輯出錯，或程序健壯性差等問題；C 表示錯誤來源於第三方服務，比如 CDN
服務出錯，消息投遞超時等問題；四位数字編號從 0001 到 9999，大類之間的步長間距預留 100，參考文末附表 3。 
5.【強制】編號不與公司業務架構，更不與組織架構掛鈎，以先到先得的原則在統一平台上進行，審批生
效，編號即被永久固定。 
6.【強制】錯誤碼使用者避免隨意定義新的錯誤碼。 
說明：盡可能在原有錯誤碼附表中找到語義相同或者相近的錯誤碼在代碼中使用即可。 
7.【強制】錯誤碼不能直接輸出給用戶作為提示信息使用。 
說明：堆棧（stack_trace）、錯誤信息(error_message) 、錯誤碼（error_code）、提示信息（user_tip）是一個有效關
聯並互相轉義的和諧整體，但是請勿互相越俎代庖。 
8.【推薦】錯誤碼之外的業務信息由 error_message 來承載，而不是讓錯誤碼本身涵蓋過多具體業務屬性。 
9.【推薦】在獲取第三方服務錯誤碼時，向上拋出允許本系統轉義，由 C 轉為 B，並且在錯誤信息上帶上原
有的第三方錯誤碼。 
10.【參考】錯誤碼分為一級宏觀錯誤碼、二級宏觀錯誤碼、三級宏觀錯誤碼。 
說明：在無法更加具體確定的錯誤場景中，可以直接使用一級宏觀錯誤碼，分別是：A0001（用戶端錯誤）、B0001（系
統執行出錯）、C0001（調用第三方服務出錯）。 
正例：調用第三方服務出錯是一級，中間件錯誤是二級，消息服務出錯是三級。
 
11.【參考】錯誤碼的后三位編號與 HTTP 狀態碼沒有任何關係。 
12.【參考】錯誤碼有利於不同文化背景的開發者進行交流與代碼協作。 
說明：英文單詞形式的錯誤碼不利於非英語母語國家（如阿拉伯語、希伯來語、俄羅斯語等）之間的開發者互相協作。 
13.【參考】錯誤碼即人性，感性認知+口口相傳，使用純数字來進行錯誤碼編排不利於感性記憶和分類。 
說明：数字是一個整體，每位数字的地位和含義是相同的。 
反例：一個五位数字 12345，第 1 位是錯誤等級，第 2 位是錯誤來源，345 是編號，人的大腦不會主動地拆開並分辨每
位数字的不同含義。 
 
Java 開發手冊（黃山版） 
 
25/51 
(二) 異常處理 
1.【強制】Java 類庫中定義的可以通過預檢查方式規避的 RuntimeException 異常不應該通過 catch 的方 
式來處理，比如：NullPointerException，IndexOutOfBoundsException 等等。 
說明：無法通過預檢查的異常除外，比如，在解析字符串形式的数字時，可能存在数字格式錯誤，不得不通過 catch 
NumberFormatException 來實現。 
正例：if (obj != null) {...} 
反例：try { obj.method(); } catch (NullPointerException e) {…} 
2.【強制】異常捕獲后不要用來做流程控制，條件控制。 
說明：異常設計的初衷是解決程序運行中的各種意外情況，且異常的處理效率比條件判斷方式要低很多。 
3.【強制】catch 時請分清穩定代碼和非穩定代碼，穩定代碼指的是無論如何不會出錯的代碼。對於非穩定
代碼的 catch 盡可能進行區分異常類型，再做對應的異常處理。 
說明：對大段代碼進行 try-catch，使程序無法根據不同的異常做出正確的應激反應，也不利於定位問題，這是一種不負
責任的表現。 
正例：用戶註冊的場景中，如果用戶輸入非法字符，或用戶名稱已存在，或用戶輸入密碼過於簡單，在程序上作出分門
別類的判斷，並提示給用戶。 
4.【強制】捕獲異常是為了處理它，不要捕獲了卻什麼都不處理而拋棄之，如果不想處理它，請將該異常
拋給它的調用者。最外層的業務使用者，必須處理異常，將其轉化為用戶可以理解的內容。 
5.【強制】事務場景中，拋出異常被 catch 后，如果需要回滾，一定要注意手動回滾事務。 
6.【強制】finally 塊必須對資源對象、流對象進行關閉，有異常也要做 try-catch。 
說明：如果 JDK7，可以使用 try-with-resources 方式。 
7.【強制】不要在 finally 塊中使用 return 
說明：try 塊中的 return 語句執行成功后，並不馬上返回，而是繼續執行 finally 塊中的語句，如果此處存在 return 語句，
則會在此直接返回，無情丟棄掉 try 塊中的返回點。 
反例： 
private int x = 0; 
public int checkReturn() { 
try { 
// x 等於 1，此處不返回 
return ++x; 
} finally { 
// 返回的結果是 2 
return ++x; 
} 
} 
8.【強制】捕獲異常與拋異常，必須是完全匹配，或者捕獲異常是拋異常的父類。 
說明：如果預期對方拋的是繡球，實際接到的是鉛球，就會產生意外情況。 
9.【強制】在調用 RPC、二方包、或動態生成類的相關方法時，捕捉異常使用 Throwable 類進行攔截。 
說明：通過反射機制來調用方法，如果找不到方法，拋出 NoSuchMethodException。什麼情況會拋出
NoSuchMethodError 呢？二方包在類衝突時，仲裁機制可能導致引入非預期的版本使類的方法簽名不匹配，或者在
字節碼修改框架（比如：ASM）動態創建或修改類時，修改了相應的方法簽名。這些情況，即使代碼編譯期是正確
的，但在代碼運行期時，會拋出 NoSuchMethodError。 
反例：足跡服務引入了高版本的 spring，導致運行到某段核心邏輯時，拋出 NoSuchMethodError 錯誤，catch 用的
類卻是 Exception，堆棧向上拋，影響到上層業務。這是一個非核心功能點影響到核心應用的典型反例。 
Java 開發手冊（黃山版） 
 
26/51 
10.【推薦】方法的返回值可以為 null，不強制返回空集合，或者空對象等，必須添加註釋充分說明什麼情
況下會返回 null 值。 
說明：本規約明確防止 NPE 是調用者的責任。即使被調用方法返回空集合或者空對象，對調用者來說，也並非高枕無
憂，必須考慮到遠程調用失敗，運行時異常等場景返回 null 的情況。 
11.【推薦】防止 NPE，是程序員的基本修養，注意 NPE 產生的場景： 
1）返回類型為基本數據類型，return 包裝數據類型的對象時，自動拆箱有可能產生 NPE 
反例：public int method() { return Integer 對象; }，如果為 null，自動解箱拋 NPE。 
2）數據庫的查詢結果可能為 null。 
3）集合里的元素即使 isNotEmpty，取出的數據元素也可能為 null。 
4）遠程調用返回對象時，一律要求進行空指針判斷，防止 NPE。 
5）對於 Session 中獲取的數據，建議進行 NPE 檢查，避免空指針。 
6）級聯調用 obj.getA().getB().getC()；一連串調用，易產生 NPE。 
正例：使用 JDK8 的 Optional 類來防止 NPE 問題。 
12.【推薦】定義時區分 unchecked / checked 異常，避免直接拋出 new RuntimeException()，更不允許
拋出 Exception 或者 Throwable，應使用有業務含義的自定義異常。推薦業界已定義過的自定義異
常，如：DAOException / ServiceException 等。 
13.【參考】對於公司外的 http / api 開放接口必須使用錯誤碼，而應用內部推薦異常拋出；跨應用間
RPC 調用優先考慮使用 Result 方式，封裝 isSuccess() 方法、錯誤碼、錯誤簡短信息；應用內部推薦
異常拋出。 
 說明：關於 RPC 方法返回方式使用 Result 方式的理由： 
1）使用拋異常返回方式，調用方如果沒有捕獲到就會產生運行時錯誤。 
2）如果不加棧信息，只是 new 自定義異常，加入自己的理解的 error message，對於調用端解決問題的幫助不會太多。
如果加了棧信息，在頻繁調用出錯的情況下，數據序列化和傳輸的性能損耗也是問題。 
 
(三) 日誌規約 
1.【強制】應用中不可直接使用日誌系統（Log4j、Logback）中的 API，而應依賴使用日誌框架（SLF4J、
JCL—Jakarta Commons Logging）中的 API，使用門面模式的日誌框架，有利於維護和各個類的日誌處理
方式統一。 
說明：日誌框架（SLF4J、JCL--Jakarta Commons Logging）的使用方式（推薦使用 SLF4J） 
使用 SLF4J： 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
private static final Logger logger = LoggerFactory.getLogger(Test.class); 
使用 JCL： 
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory; 
private static final Log log = LogFactory.getLog(Test.class); 
2.【強制】日誌文件至少保存 15 天，因為有些異常具備以“周”為頻次發生的特點。對於當天日誌，以
“應用名.log”來保存，保存在/{統一目錄}/{應用名}/logs/目錄下，過往日誌格式為：
{logname}.log.{保存日期}，日期格式：yyyy-MM-dd 
正例：以 mppserver 應用為例，日誌保存/home/admin/mppserver/logs/mppserver.log，歷史日誌名稱
為 mppserver.log.2021-11-28 
Java 開發手冊（黃山版） 
 
27/51 
3.【強制】根據國家法律，網絡運行狀態、網絡安全事件、個人敏感信息操作等相關記錄，留存的日誌不
少於六個月，並且進行網絡多機備份。 
4.【強制】應用中的擴展日誌（如打點、臨時監控、訪問日誌等）命名方式：
appName_logType_logName.log。logType：日誌類型，如 stats / monitor / access 等；
logName：日誌描述。這種命名的好處：通過文件名就可知道日誌文件屬於什麼應用，什麼類型，什
么目的，也有利於歸類查找。 
說明：推薦對日誌進行分類，將錯誤日誌和業務日誌分開放，便於開發人員查看，也便於通過日誌對系統進行及時監控。 
正例：mppserver 應用中單獨監控時區轉換異常，如：mppserver_monitor_timeZoneConvert.log 
5.【強制】在日誌輸出時，字符串變量之間的拼接使用佔位符的方式。 
說明：因為 String 字符串的拼接會使用 StringBuilder 的 append() 方式，有一定的性能損耗。使用佔位符僅是替換動
作，可以有效提升性能。 
正例：logger.debug("Processing trade with id : {} and symbol : {}", id, symbol); 
6.【強制】對於 trace / debug / info 級別的日誌輸出，必須進行日誌級別的開關判斷： 
說明：雖然在 debug(參數) 的方法體內第一行代碼 isDisabled(Level.DEBUG_INT) 為真時（Slf4j 的常見實現 Log4j 和
Logback），就直接 return，但是參數可能會進行字符串拼接運算。此外，如果 debug(getName()) 這種參數內有
getName() 方法調用，無謂浪費方法調用的開銷。 
正例： 
// 如果判斷為真，那麼可以輸出 trace 和 debug 級別的日誌 
if (logger.isDebugEnabled()) { 
logger.debug("Current ID is: {} and name is: {}", id, getName()); 
} 
7.【強制】避免重複打印日誌，浪費磁盤空間，務必在日誌配置文件中設置 additivity=false 
正例：<logger name="com.taobao.dubbo.config" additivity="false"> 
8.【強制】生產環境禁止使用 System.out 或 System.err 輸出或使用 e.printStackTrace() 打印異常堆棧。 
說明：標準日誌輸出與標準錯誤輸出文件每次 Jboss 重啟時才滾動，如果大量輸出送往這兩個文件，容易造成文件大小
超過操作系統大小限制。 
9.【強制】異常信息應該包括兩類信息：案發現場信息和異常堆棧信息。如果不處理，那麼通過關鍵字
throws 往上拋出。 
正例：logger.error("inputParams: {} and errorMessage: {}", 各類參數或者對象 toString(), e.getMessage(), e); 
10.【強制】日誌打印時禁止直接用 JSON 工具將對象轉換成 String。 
 說明：如果對象里某些 get 方法被覆寫，存在拋出異常的情況，則可能會因為打印日誌而影響正常業務流程的執行。 
 正例：打印日誌時僅打印出業務相關屬性值或者調用其對象的 toString() 方法。 
11.【推薦】謹慎地記錄日誌。生產環境禁止輸出 debug 日誌；有選擇地輸出 info 日誌；如果使用 warn
來記錄剛上線時的業務行為信息，一定要注意日誌輸出量的問題，避免把服務器磁盤撐爆，並記得及時
刪除這些觀察日誌。 
說明：大量地輸出無效日誌，不利於系統性能提升，也不利於快速定位錯誤點。記錄日誌時請思考：這些日誌真的有
人看嗎？看到這條日誌你能做什麼？能不能給問題排查帶來好處？ 
12.【推薦】可以使用 warn 日誌級別來記錄用戶輸入參數錯誤的情況，避免用戶投訴時，無所適從。如非
必要，請不要在此場景打出 error 級別，避免頻繁報警。 
 說明：注意日誌輸出的級別，error 級別只記錄系統邏輯出錯、異常或者重要的錯誤信息。 
13.【推薦】盡量用英文來描述日誌錯誤信息，如果日誌中的錯誤信息用英文描述不清楚的話使用中文描述
即可，否則容易產生歧義。 
Java 開發手冊（黃山版） 
 
28/51 
 說明：國際化團隊或海外部署的服務器由於字符集問題，使用全英文來註釋和描述日誌錯誤信息。 
14.【推薦】為了保護用戶隱私，日誌文件中的用戶敏感信息需要進行脫敏處理。 
說明：日誌排查問題時，推薦使用訂單號、UUID 之類的唯一編號進行查詢。 
 
Java 開發手冊（黃山版） 
 
29/51 
三、單元測試 
1.【強制】好的單元測試必須遵守 AIR 原則。 
說明：單元測試在線上運行時，感覺像空氣（AIR）一樣感覺不到，但在測試質量的保障上，卻是非常關鍵的。好的單元
測試宏觀上來說，具有自動化、獨立性、可重複執行的特點。 
⚫ A：Automatic（自動化） 
⚫ I：Independent（獨立性） 
⚫ R：Repeatable（可重複） 
2.【強制】單元測試應該是全自動執行的，並且非交互式的。測試用例通常是被定期執行的，執行過程必須
完全自動化才有意義。輸出結果需要人工檢查的測試不是一個好的單元測試。不允許使用 System.out 來
進行人肉驗證，單元測試必須使用 assert 來驗證。 
3.【強制】保持單元測試的獨立性。為了保證單元測試穩定可靠且便於維護，單元測試用例之間決不能互相
調用，也不能依賴執行的先後次序。 
反例：method2 需要依賴 method1 的執行，將執行結果作為 method2 的輸入。 
4.【強制】單元測試是可以重複執行的，不能受到外界環境的影響。 
說明：單元測試通常會被放到持續集成中，每次有代碼 push 時單元測試都會被執行。如果單測對外部環境（網絡、服
務、中間件等）有依賴，容易導致持續集成機制的不可用。 
正例：為了不受外界環境影響，要求設計代碼時就把 SUT（Software under test）的依賴改成注入，在測試時用 Spring
這樣的 DI 框架注入一個本地（內存）實現或者 Mock 實現。 
5.【強制】對於單元測試，要保證測試粒度足夠小，有助於精確定位問題。單測粒度至多是類級別，一般是
方法級別。 
說明：測試粒度小才能在出錯時儘快定位到出錯的位置。單元測試不負責檢查跨類或者跨系統的交互邏輯，那是集成測試
的領域。 
6.【強制】核心業務、核心應用、核心模塊的增量代碼確保單元測試通過。 
說明：新增代碼及時補充單元測試，如果新增代碼影響了原有單元測試，請及時修正。 
7.【強制】單元測試代碼必須寫在如下工程目錄： src/test/java，不允許寫在業務代碼目錄下。 
說明：源碼編譯時會跳過此目錄，而單元測試框架默認是掃描此目錄。 
8.【推薦】單測的基本目標：語句覆蓋率達到 70%；核心模塊的語句覆蓋率和分支覆蓋率都要達到 100% 
說明：在工程規約的應用分層中提到的 DAO 層，Manager 層，可重用度高的 Service，都應該進行單元測試。 
9.【推薦】編寫單元測試代碼遵守 BCDE 原則，以保證被測試模塊的交付質量。 
⚫ B：Border，邊界值測試，包括循環邊界、特殊取值、特殊時間點、數據順序等。 
⚫ C：Correct，正確的輸入，並得到預期的結果。 
⚫ D：Design，與設計文檔相結合，來編寫單元測試。 
⚫ E：Error，強制錯誤信息輸入（如：非法數據、異常流程、業務允許外等），並得到預期的結果。 
10.【推薦】對於數據庫相關的查詢，更新，刪除等操作，不能假設數據庫里的數據是存在的，或者直接操
作數據庫把數據插入進去，請使用程序插入或者導入數據的方式來準備數據。 
反例：刪除某一行數據的單元測試，在數據庫中，先直接手動增加一行作為刪除目標，但是這一行新增數據並不符合業
務插入規則，導致測試結果異常。 
11.【推薦】和數據庫相關的單元測試，可以設定自動回滾機制，不給數據庫造成臟數據。或者對單元測試
產生的數據有明確的前後綴標識。 
正例：在基礎技術部的內部單元測試中，使用 FOUNDATION_UNIT_TEST_的前綴來標識單元測試相關代碼。 
Java 開發手冊（黃山版） 
 
30/51 
12.【推薦】對於不可測的代碼在適當的時機做必要的重構，使代碼變得可測避免為了達到測試要求而書寫
不規範測試代碼。 
13.【推薦】在設計評審階段，開發人員需要和測試人員一起確定單元測試範圍，單元測試最好覆蓋所有測
試用例（UC）。 
14.【推薦】單元測試作為一種質量保障手段，在項目提測前完成單元測試，不建議項目發布後補充單元測
試用例。 
15.【參考】為了更方便地進行單元測試，業務代碼應避免以下情況： 
⚫ 構造方法中做的事情過多。 
⚫ 存在過多的全局變量和靜態方法。 
⚫ 存在過多的外部依賴。 
⚫ 存在過多的條件語句。 
說明：多層條件語句建議使用衛語句、策略模式、狀態模式等方式重構。 
16.【參考】不要對單元測試存在如下誤解： 
⚫ 那是測試同學乾的事情。本文是開發手冊，凡是本文內容都是與開發同學強相關的。 
⚫ 單元測試代碼是多餘的。系統的整體功能與各單元部件的測試正常與否是強相關的。 
⚫ 單元測試代碼不需要維護。一年半載后，那麼單元測試幾乎處於廢棄狀態。 
⚫ 單元測試與線上故障沒有辯證關係。好的單元測試能夠最大限度地規避線上故障。
Java 開發手冊（黃山版） 
 
31/51 
四、安全規約 
1.【強制】隸屬於用戶個人的頁面或者功能必須進行權限控制校驗。 
說明：防止沒有做水平權限校驗就可隨意訪問、修改、刪除別人的數據，比如查看他人的私信內容。 
2.【強制】用戶敏感數據禁止直接展示，必須對展示數據進行脫敏。 
正例：中國大陸個人手機號碼显示：139****1219，隱藏中間 4 位，防止隱私泄露。 
3.【強制】用戶輸入的 SQL 參數嚴格使用參數綁定或者 METADATA 字段值限定，防止 SQL 注入，禁止字
符串拼接 SQL 訪問數據庫。 
反例：某系統簽名大量被惡意修改，即是因為對於危險字符#--沒有進行轉義，導致數據庫更新時，where 後邊的信息被注
釋掉，對全庫進行更新。 
4.【強制】用戶請求傳入的任何參數必須做有效性驗證。 
說明：忽略參數校驗可能導致： 
⚫ 頁面 page size 過大導致內存溢出 
⚫ 惡意 order by 導致數據庫慢查詢 
⚫ 緩存擊穿 
⚫ SSRF 
⚫ 任意重定向 
⚫ SQL 注入，Shell 注入，反序列化注入 
⚫ 正則輸入源串拒絕服務 ReDoS 
擴展：Java 代碼用正則來驗證客戶端的輸入，有些正則寫法驗證普通用戶輸入沒有問題，但是如果攻擊人員使
用的是特殊構造的字符串來驗證，有可能導致死循環的結果。 
5.【強制】禁止向 HTML 頁面輸出未經安全過濾或未正確轉義的用戶數據。 
說明：XSS 跨站腳本攻擊。它指的是惡意攻擊者往 Web 頁面里插入惡意 html 代碼，當用戶瀏覽時，嵌入其中 Web 里
面的 html 代碼會被執行，造成獲取用戶 cookie、釣魚、獲取用戶頁面數據、蠕蟲、掛馬等危害。 
6.【強制】表單、AJAX 提交必須執行 CSRF 安全驗證。 
說明：CSRF (Cross-site request forgery) 跨站請求偽造是一類常見編程漏洞。對於存在 CSRF 漏洞的應用/網站，攻擊
者可以事先構造好 URL，只要受害者用戶一訪問，後台便在用戶不知情的情況下對數據庫中用戶參數進行相應修改。 
7.【強制】URL 外部重定向傳入的目標地址必須執行白名單過濾。 
說明：攻擊者通過惡意構造跳轉的鏈接，可以向受害者發起釣魚攻擊。 
8.【強制】在使用平台資源，譬如短信、郵件、電話、下單、支付，必須實現正確的防重放的機制，如數量
限制、疲勞度控制、驗證碼校驗，避免被濫刷而導致資損。 
說明：如註冊時發送驗證碼到手機，如果沒有限制次數和頻率，那麼可以利用此功能騷擾到其它用戶，並造成短信平台
資源浪費。 
9.【強制】對於文件上傳功能，需要對於文件大小、類型進行嚴格檢查和控制。 
說明：攻擊者可以利用上傳漏洞，上傳惡意文件到服務器，並且遠程執行，達到控制網站服務器的目的。 
10.【強制】配置文件中的密碼需要加密。 
11.【推薦】發貼、評論、發送等即時消息，需要用戶輸入內容的場景。必須實現防刷、內容違禁詞過濾等
風控策略。
Java 開發手冊（黃山版） 
 
32/51 
五、MySQL 數據庫 
(一) 建表規約 
1.【強制】表達是與否概念的字段，必須使用 is_xxx 的方式命名，數據類型是 unsigned tinyint（1 表示
是，0 表示否）。 
注意：POJO 類中的任何布爾類型的變量，都不要加 is 前綴，所以，需要在<resultMap>設置從 is_xxx 到 Xxx 的映射關
系。數據庫表示是與否的值，使用 tinyint 類型，堅持 is_xxx 的命名方式是為了明確其取值含義與取值範圍。 
說明：任何字段如果為非負數，必須是 unsigned。 
正例：表達邏輯刪除的字段名 is_deleted，1 表示刪除，0 表示未刪除。 
2.【強制】表名、字段名必須使用小寫字母或数字，禁止出現数字開頭禁止兩個下劃線中間只出現数字。數
據庫字段名的修改代價很大，因為無法進行預發布，所以字段名稱需要慎重考慮。 
說明：MySQL 在 Windows 下不區分大小寫，但在 Linux 下默認是區分大小寫。因此，數據庫名、表名、字段名，都不允
許出現任何大寫字母，避免節外生枝。 
正例：aliyun_admin，rdc_config，level3_name 
反例：AliyunAdmin，rdcConfig，level_3_name 
3.【強制】表名不使用複數名詞。 
說明：表名應該僅僅表示表裡面的實體內容，不應該表示實體數量，對應於 DO 類名也是單數形式，符合表達習慣。 
4.【強制】禁用保留字，如 desc、range、match、delayed 等，請參考 MySQL 官方保留字。 
5.【強制】主鍵索引名為 pk_字段名；唯一索引名為 uk_字段名；普通索引名則為 idx_字段名。 
說明：pk_即 primary key；uk_即 unique key；idx_即 index 的簡稱。 
6.【強制】小數類型為 decimal，禁止使用 float 和 double。 
說明：在存儲的時候，float 和 double 都存在精度損失的問題，很可能在比較值的時候，得到不正確的結果。如果存
儲的數據範圍超過 decimal 的範圍，建議將數據拆成整數和小數並分開存儲。 
7.【強制】如果存儲的字符串長度幾乎相等，使用 char 定長字符串類型。 
8.【強制】varchar 是可變長字符串，不預先分配存儲空間，長度不要超過 5000，如果存儲長度大於此
值，定義字段類型為 text，獨立出來一張表，用主鍵來對應，避免影響其它字段索引率。 
9.【強制】表必備三字段：id，create_time，update_time。 
說明：其中 id 必為主鍵，類型為 bigint unsigned、單表時自增、步長為 1。create_time，update_time 的類型均為
datetime 類型，如果要記錄時區信息，那麼類型設置為 timestamp。 
10.【強制】在數據庫中不能使用物理刪除操作，要使用邏輯刪除。 
說明：邏輯刪除在數據刪除后可以追溯到行為操作。不過會使得一些情況下的唯一主鍵變得不唯一，需要根據情況來酌
情解決。 
11.【推薦】表的命名最好是遵循“業務名稱_表的作用”。 
正例：alipay_task / force_project / trade_config / tes_question 
12.【推薦】庫名與應用名稱盡量一致。 
13.【推薦】如果修改字段含義或對字段表示的狀態追加時，需要及時更新字段註釋。 
14.【推薦】字段允許適當冗餘，以提高查詢性能，但必須考慮數據一致。冗餘字段應遵循： 
1）不是頻繁修改的字段。 
2）不是唯一索引的字段。 
3）不是 varchar 超長字段，更不能是 text 字段。 
正例：各業務線經常冗餘存儲商品名稱，避免查詢時需要調用 IC 服務獲取。 
Java 開發手冊（黃山版） 
 
33/51 
15.【推薦】單錶行數超過 500 萬行或者單表容量超過 2GB，才推薦進行分庫分表。 
說明：如果預計三年後的數據量根本達不到這個級別，請不要在創建表時就分庫分表。 
16.【參考】合適的字符存儲長度，不但節約數據庫表空間、節約索引存儲，更重要的是提升檢索速度。 
 正例：無符號值可以避免誤存負數，且擴大了表示範圍： 
對象 
年齡區間 
類型 
字節 
表示範圍 
人 
150 歲之內 
tinyint unsigned 
1 
無符號值：0 到 255 
龜 
數百歲 
smallint unsigned 
2 
無符號值：0 到 65535 
恐龍化石 
數千萬年 
int unsigned 
4 
無符號值：0 到約 43 億 
太陽 
約 50 億年 
bigint unsigned 
8 
無符號值：0 到約 10 的 19 次方 
 
(二) 索引規約 
1.【強制】業務上具有唯一特性的字段，即使是組合字段，也必須建成唯一索引。 
說明：不要以為唯一索引影響了 insert 速度，這個速度損耗可以忽略，但提高查找速度是明顯的；另外，即使在應用層
做了非常完善的校驗控制，只要沒有唯一索引，根據墨菲定律，必然有臟數據產生。 
2.【強制】超過三個表禁止 join。需要 join 的字段，數據類型保持絕對一致；多表關聯查詢時，保證被關聯
的字段需要有索引。 
說明：即使雙表 join 也要注意表索引、SQL 性能。 
3.【強制】在 varchar 字段上建立索引時，必須指定索引長度，沒必要對全字段建立索引，根據實際文本區
分度決定索引長度。 
說明：索引的長度與區分度是一對矛盾體，一般對字符串類型數據，長度為 20 的索引，區分度會高達 90%以上，可以使
用 count(distinct left(列名，索引長度)) / count(*) 的區分度來確定。 
4.【強制】頁面搜索嚴禁左模糊或者全模糊，如果需要請走搜索引擎來解決。 
說明：索引文件具有 B-Tree 的最左前綴匹配特性，如果左邊的值未確定，那麼無法使用此索引。 
5.【推薦】如果有 order by 的場景，請注意利用索引的有序性。order by 最後的字段是組合索引的一部
分，並且放在索引組合順序的最後，避免出現 filesort 的情況，影響查詢性能。 
正例：where a = ? and b = ? order by c；索引：a_b_c 
反例：索引如果存在範圍查詢，那麼索引有序性無法利用，如：WHERE a > 10 ORDER BY b；索引 a_b 無法排序。 
6.【推薦】利用覆蓋索引來進行查詢操作，避免回表。 
說明：如果一本書需要知道第 11 章是什麼標題，會翻開第 11 章對應的那一頁嗎？目錄瀏覽一下就好，這個目錄就是起
到覆蓋索引的作用。 
正例：能夠建立索引的種類分為主鍵索引、唯一索引、普通索引三種，而覆蓋索引只是一種查詢的一種效果，用 explain
的結果，extra 列會出現：using index。 
7.【推薦】利用延遲關聯或者子查詢優化超多分頁場景。 
說明：MySQL 並不是跳過 offset 行，而是取 offset+N 行，然後返回放棄前 offset 行，返回 N 行，那當 offset 特別大
的時候，效率就非常的低下，要麼控制返回的總頁數，要麼對超過特定閾值的頁數進行 SQL 改寫。 
正例：先快速定位需要獲取的 id 段，然後再關聯： 
SELECT t1.* FROM 表 1 as t1 , (select id from 表 1 where 條件 LIMIT 100000 , 20) as t2 where t1.id = t2.id  
8.【推薦】SQL 性能優化的目標：至少要達到 range 級別，要求是 ref 級別，如果可以是 const 最好。 
說明： 
1）consts 單表中最多只有一個匹配行（主鍵或者唯一索引），在優化階段即可讀取到數據。 
Java 開發手冊（黃山版） 
 
34/51 
2）ref 指的是使用普通的索引（normal index）。 
3）range 對索引進行範圍檢索。 
反例：explain 表的結果，type = index，索引物理文件全掃描，速度非常慢，這個 index 級別比較 range 還低，與全
表掃描是小巫見大巫。 
9.【推薦】建組合索引的時候，區分度最高的在最左邊。 
正例：如果 where a = ? and b = ?，a 列的幾乎接近於唯一值，那麼只需要單建 idx_a 索引即可。 
說明：存在非等號和等號混合判斷條件時，在建索引時，請把等號條件的列前置。如：where c > ? and d = ? 那麼即使
c 的區分度更高，也必須把 d 放在索引的最前列，即建立組合索引 idx_d_c。 
10.【推薦】防止因字段類型不同造成的隱式轉換，導致索引失效。 
11.【參考】創建索引時避免有如下極端誤解： 
1）索引寧濫勿缺。認為一個查詢就需要建一個索引。 
2）吝嗇索引的創建。認為索引會消耗空間、嚴重拖慢記錄的更新以及行的新增速度。 
3）抵制唯一索引。認為唯一索引一律需要在應用層通過“先查后插”方式解決。 
 
(三) SQL 語句 
1.【強制】不要使用 count(列名) 或 count(常量) 來替代 count(*)，count(*) 是 SQL92 定義的標準統計行
數的語法，跟數據庫無關，跟 NULL 和非 NULL 無關。 
說明：count(*) 會統計值為 NULL 的行，而 count(列名) 不會統計此列為 NULL 值的行。 
2.【強制】count(distinct col) 計算該列除 NULL 之外的不重複行數，注意 count(distinct col1 , col2) 如
果其中一列全為 NULL，那麼即使另一列有不同的值，也返回為 0。 
3.【強制】當某一列的值全是 NULL 時，count(col) 的返回結果為 0；但 sum(col) 的返回結果為 NULL，因
此使用 sum() 時需注意 NPE 問題。 
正例：可以使用如下方式來避免 sum 的 NPE 問題：SELECT IFNULL(SUM(column) , 0) FROM table; 
4.【強制】使用 ISNULL() 來判斷是否為 NULL 值。 
說明：NULL 與任何值的直接比較都為 NULL。 
1）NULL<>NULL 的返回結果是 NULL，而不是 false。 
2）NULL=NULL 的返回結果是 NULL，而不是 true。 
3）NULL<>1 的返回結果是 NULL，而不是 true。 
反例：在 SQL 語句中，如果在 null 前換行，影響可讀性。 
select * from table where column1 is null and column3 is not null；而 ISNULL(column) 是一個整體，簡潔易懂。
從性能數據上分析，ISNULL(column) 執行效率更快一些。 
5.【強制】代碼中寫分頁查詢邏輯時，若 count 為 0 應直接返回，避免執行後面的分頁語句。 
6.【強制】不得使用外鍵與級聯，一切外鍵概念必須在應用層解決。 
說明：（概念解釋）學生表中的 student_id 是主鍵，那麼成績表中的 student_id 則為外鍵。如果更新學生表中的
student_id，同時觸發成績表中的 student_id 更新，即為級聯更新。外鍵與級聯更新適用於單機低併發，不適合分佈式、
高併發集群；級聯更新是強阻塞，存在數據庫更新風暴的風險；外鍵影響數據庫的插入速度。 
7.【強制】禁止使用存儲過程，存儲過程難以調試和擴展，更沒有移植性。 
8.【強制】數據訂正（特別是刪除或修改記錄操作）時，要先 select，避免出現誤刪除的情況，確認無誤才
能執行更新語句。 
9.【強制】對於數據庫中表記錄的查詢和變更，只要涉及多個表，都需要在列名前加表的別名（或表名）進
行限定。 
Java 開發手冊（黃山版） 
 
35/51 
說明：對多表進行查詢記錄、更新記錄、刪除記錄時，如果對操作列沒有限定表的別名（或表名），並且操作列在多個
表中存在時，就會拋異常。 
正例：select t1.name from first_table as t1 , second_table as t2 where t1.id = t2.id; 
反例：在某業務中，由於多表關聯查詢語句沒有加表的別名（或表名）的限制，正常運行兩年後，最近在某個表中增加
一個同名字段，在預發布環境做數據庫變更后，線上查詢語句出現出 1052 異常： 
Column 'name' infield list is ambiguous。 
10.【推薦】SQL 語句中表的別名前加 as，並且以 t1、t2、t3、...的順序依次命名。 
說明： 
1）別名可以是表的簡稱，或者是依照表在 SQL 語句中出現的順序，以 t1、t2、t3 的方式命名。 
2）別名前加 as 使別名更容易識別。 
正例：select t1.name from first_table as t1 , second_table as t2 where t1.id = t2.id; 
11.【推薦】in 操作能避免則避免，若實在避免不了，需要仔細評估 in 後邊的集合元素數量，控制在
1000 個之內。 
12.【參考】因國際化需要，所有的字符存儲與表示，均採用 utf8mb4 字符集，字符計數方法需要注意。 
 說明： 
SELECT LENGTH("輕鬆工作")；--返回為 12 
SELECT CHARACTER_LENGTH("輕鬆工作")；--返回為 4 
表情需要用 utf8mb4 來進行存儲，注意它與 utf8 編碼的區別。 
13.【參考】TRUNCATE TABLE 比 DELETE 速度快，且使用的系統和事務日誌資源少，但 TRUNCATE 
 無事務且不觸發 trigger，有可能造成事故，故不建議在開發代碼中使用此語句。 
 說明：TRUNCATE TABLE 在功能上與不帶 WHERE 子句的 DELETE 語句相同。 
 
(四) ORM 映射 
1.【強制】在表查詢中，一律不要使用 * 作為查詢的字段列表，需要哪些字段必須明確寫明。 
說明： 
1）增加查詢分析器解析成本。 
2）增減字段容易與 resultMap 配置不一致。 
3）無用字段增加網絡消耗，尤其是 text 類型的字段。 
2.【強制】POJO 類的布爾屬性不能加 is，而數據庫字段必須加 is_，要求在 resultMap 中進行字段與屬
性之間的映射。 
說明：參見定義 POJO 類以及數據庫字段定義規定，在 sql.xml 增加映射，是必須的。 
3.【強制】不要用 resultClass 當返回參數，即使所有類屬性名與數據庫字段一一對應，也需要定義
<resultMap>；反過來，每一個表也必然有一個<resultMap>與之對應。 
說明：配置映射關係，使字段與 DO 類解耦，方便維護。 
4.【強制】sql.xml 配置參數使用：#{}，#param# 不要使用 ${} 此種方式容易出現 SQL 注入。 
5.【強制】iBATIS 自帶的 queryForList(String statementName，int start，int size) 不推薦使用。 
說明：其實現方式是在數據庫取到 statementName 對應的 SQL 語句的所有記錄，再通過 subList 取 start，size
的子集合，線上因為這個原因曾經出現過 OOM。 
正例： 
Map<String, Object> map = new HashMap<>(16); 
map.put("start", start); 
map.put("size", size); 
Java 開發手冊（黃山版） 
 
36/51 
6.【強制】不允許直接拿 HashMap 與 Hashtable 作為查詢結果集的輸出。 
反例：某同學為避免寫一個<resultMap>xxx</resultMap>，直接使用 Hashtable 來接收數據庫返回結果，結果出現
日常是把 bigint 轉成 Long 值，而線上由於數據庫版本不一樣，解析成 BigInteger，導致線上問題。 
7.【強制】更新數據表記錄時，必須同時更新記錄對應的 update_time 字段值為當前時間。 
8.【推薦】不要寫一個大而全的數據更新接口。傳入為 POJO 類，不管是不是自己的目標更新字段，都進行
update table set c1 = value1 , c2 = value2 , c3 = value3；這是不對的。執行 SQL 時，不要更新無改
動的字段，一是易出錯；二是效率低；三是增加 binlog 存儲。 
9.【參考】@Transactional 事務不要濫用。事務會影響數據庫的 QPS，另外使用事務的地方需要考慮各
方面的回滾方案，包括緩存回滾、搜索引擎回滾、消息補償、統計修正等。 
10.【參考】<isEqual>中的 compareValue 是與屬性值對比的常量，一般是数字，表示相等時帶上此條
件；<isNotEmpty>表示不為空且不為 null 時執行；<isNotNull>表示不為 null 值時執行。
Java 開發手冊（黃山版） 
 
37/51 
六、工程結構 
(一) 應用分層 
1.【推薦】根據業務架構實踐，結合業界分層規範與流行技術框架分析，推薦分層結構如圖所示，默認上層
依賴於下層，箭頭關係表示可直接依賴，如：開放 API 層可以依賴於 Web 層（Controller 層），也可以
直接依賴於 Service 層，依此類推： 
 
⚫ 開放 API 層：可直接封裝 Service 接口暴露成 RPC 接口；通過 Web 封裝成 http 接口；網關控制層等。 
⚫ 終端显示層：各個端的模板渲染並執行显示的層。當前主要是 velocity 渲染，JS 渲染，JSP 渲染，移動端展示等。 
⚫ Web 層：主要是對訪問控制進行轉發，各類基本參數校驗，或者不復用的業務簡單處理等。 
⚫ Service 層：相對具體的業務邏輯服務層。 
⚫ Manager 層：通用業務處理層，它有如下特徵 
1）對第三方平台封裝的層，預處理返回結果及轉化異常信息，適配上層接口。 
2）對 Service 層通用能力的下沉，如緩存方案、中間件通用處理。 
3）與 DAO 層交互，對多個 DAO 的組合復用。 
⚫ DAO 層：數據訪問層，與底層 MySQL、Oracle、Hbase、OceanBase 等進行數據交互。 
⚫ 第三方服務：包括其它部門 RPC 服務接口，基礎平台，其它公司的 HTTP 接口，如淘寶開放平台、支付寶付款服務、
高德地圖服務等。 
⚫ 外部數據接口：外部（應用）數據存儲服務提供的接口，多見於數據遷移場景中。 
2.【參考】（分層異常處理規約）在 DAO 層，產生的異常類型有很多，無法用細粒度的異常進行 catch，
使用 catch(Exception e) 方式，並 throw new DAOException(e)，不需要打印日誌，因為日誌在
Manager 或 Service 層一定需要捕獲並打印到日誌文件中去，如果同台服務器再打日誌，浪費性能和存
儲。在 Service 層出現異常時，必須記錄出錯日誌到磁盤，盡可能帶上參數和上下文信息，相當於保護案
發現場。Manager 層與 Service 同機部署，日誌方式與 DAO 層處理一致，如果是單獨部署，則採用與
Service 一致的處理方式。Web 層絕不應該繼續往上拋異常，因為已經處於頂層，如果意識到這個異常
將導致頁面無法正常渲染，那麼就應該直接跳轉到友好錯誤頁面，盡量加上友好的錯誤提示信息。開放
接口層要將異常處理成錯誤碼和錯誤信息方式返回。 
3.【參考】分層領域模型規約： 
⚫ DO（Data Object）：此對象與數據庫表結構一一對應，通過 DAO 層向上傳輸數據源對象。 
⚫ DTO（Data Transfer Object）：數據傳輸對象，Service 或 Manager 向外傳輸的對象。
Java 開發手冊（黃山版） 
 
 
38/51 
⚫ BO（Business Object）：業務對象，可以由 Service 層輸出的封裝業務邏輯的對象。 
⚫ Query：數據查詢對象，各層接收上層的查詢請求。注意超過 2 個參數的查詢封裝，禁止使用 Map 類來傳輸。 
⚫ VO（View Object）：显示層對象，通常是 Web 向模板渲染引擎層傳輸的對象。 
 
(二) 二方庫依賴 
1.【強制】定義 GAV 遵從以下規則： 
1）GroupId 格式：com.{公司/BU}.業務線.[子業務線]，最多 4 級。 
 
 
 
說明：{公司/BU}例如：alibaba / taobao / tmall / kaikeba 等 BU 一級；子業務線可選。 
 
 
 
正例：com.taobao.jstorm 或 com.alibaba.dubbo.register 
2）ArtifactId 格式：產品線名-模塊名。語義不重複不遺漏，先到中央倉庫去查證一下。 
 
正例：dubbo-client / fastjson-api / jstorm-tool 
3）Version：詳細規定參考下方。 
2.【強制】二方庫版本號命名方式：主版本號.次版本號.修訂號 
1）主版本號：產品方向改變，或者大規模 API 不兼容，或者架構不兼容升級。 
2）次版本號：保持相對兼容性，增加主要功能特性，影響範圍極小的 API 不兼容修改。 
3）修訂號：保持完全兼容性，修復 BUG、新增次要功能特性等。 
說明：注意起始版本號必須為：1.0.0，而不是 0.0.1。 
反例：倉庫內某二方庫版本號從 1.0.0.0 開始，一直默默“升級”成 1.0.0.64，完全失去版本的語義信息。 
3.【強制】線上應用不要依賴 SNAPSHOT 版本（安全包除外）；正式發布的類庫必須先去中央倉庫進行查
證，使 RELEASE 版本號有延續性，且版本號不允許覆蓋升級。 
說明：不依賴 SNAPSHOT 版本是保證應用發布的冪等性。另外，也可以加快編譯時的打包構建。 
4.【強制】二方庫的新增或升級，保持除功能點之外的其它 jar 包仲裁結果不變。如果有改變，必須明確評
估和驗證。 
說明：在升級時，進行 dependency:resolve 前後信息比對，如果仲裁結果完全不一致，那麼通過 dependency:tree 命
令，找出差異點，進行<exclude>排除 jar 包。 
5.【強制】二方庫里可以定義枚舉類型，參數可以使用枚舉類型，但是接口返回值不允許使用枚舉類型或者
包含枚舉類型的 POJO 對象。 
6.【強制】二方庫定製包的命名方式，在規定的版本號之後加“-英文說明[序號]”，英文說明可以是部門
簡稱、業務名稱，序號直接緊跟在英文說明之後，表示此定製包的順序號。 
說明：fastjson 給 SCM 定製的版本號：1.0.0-SCM1。注：請盡可能在應用端來解決類衝突和加載問題，避免隨意發布此 
類定製包。 
7.【強制】依賴於一個二方庫群時，必須定義一個統一的版本變量，避免版本號不一致。 
說明：依賴 springframework-core，-context，-beans，它們都是同一個版本，可以定義一個變量來保存版本：
${spring.version}，定義依賴的時候，引用該版本。 
8.【強制】禁止在子項目的 pom 依賴中出現相同的 GroupId，相同的 ArtifactId，但是不同的 Version。 
 
說明：在本地調試時會使用各子項目指定的版本號，但是合併成一個 war，只能有一個版本號出現在最後的 lib 目錄
中。曾經出現過線下調試是正確的，發布到線上卻出故障的先例。 
9.【推薦】底層基礎技術框架、核心數據管理平台、或近硬件端系統謹慎引入第三方實現。 
10.【推薦】所有 pom 文件中的依賴聲明放在<dependencies>語句塊中，所有版本仲裁放在
<dependencyManagement>語句塊中。 
Java 開發手冊（黃山版） 
 
 
39/51 
說明：<dependencyManagement>里只是聲明版本，並不實現引入，因此子項目需要顯式的聲明依賴，version 和
scope 都讀取自父 pom。而<dependencies>所有聲明在主 pom 的<dependencies>里的依賴都會自動引入，並默
認被所有的子項目繼承。 
11.【推薦】二方庫不要有配置項，最低限度不要再增加配置項。 
12.【推薦】不要使用不穩定的工具包或者 Utils 類。 
說明：不穩定指的是提供方無法做到向下兼容，在編譯階段正常，但在運行時產生異常，因此，盡量使用業界穩定的
二方工具包。 
13.【參考】為避免應用二方庫的依賴衝突問題，二方庫發布者應當遵循以下原則： 
1）
。移除一切不必要的 API 和依賴，只包含 Service API、必要的領域模型對象、Utils 類、常量、枚舉
等。如果依賴其它二方庫，盡量是 provided 引入，讓二方庫使用者去依賴具體版本號；無 log 具體實現，只依賴日誌
框架。 
2）
。每個版本的變化應該被記錄，二方庫由誰維護，源碼在哪裡，都需要能方便查到。除非用戶主動
升級版本，否則公共二方庫的行為不應該發生變化。 
 
(三) 服務器 
1.【強制】調用遠程操作必須有超時設置。 
說明：類似於 HttpClient 的超時設置需要自己明確去設置 Timeout。根據經驗表明，無數次的故障都是因為沒有設置 
超時時間。 
2.【推薦】客戶端設置遠程接口方法的具體超時時間（單位 ms），超時設置生效順序一般為：1）客戶 
端 Special Method；2）客戶端接口級別；3）服務端 Special Method；4）服務端接口級別。 
3.【推薦】高併發服務器建議調小 TCP 協議的 time_wait 超時時間。 
說明：操作系統默認 240 秒后，才會關閉處於 time_wait 狀態的連接，在高併發訪問下，服務器端會因為處於
time_wait 的連接數太多，可能無法建立新的連接，所以需要在服務器上調小此等待值。 
正例：在 linux 服務器上請通過變更/etc/sysctl.conf 文件去修改該缺省值（秒）：net.ipv4.tcp_fin_timeout=30 
4.【推薦】調大服務器所支持的最大文件句柄數（File Descriptor，簡寫為 fd） 
說明：主流操作系統的設計是將 TCP / UDP 連接採用與文件一樣的方式去管理，即一個連接對應於一個 fd。主流的 linux 
服務器默認所支持最大 fd 數量為 1024，當併發連接數很大時很容易因為 fd 不足而出現“open too many files”錯誤， 
導致新的連接無法建立。建議將 linux 服務器所支持的最大句柄數調高數倍（與服務器的內存數量相關）。 
5.【推薦】給 JVM 環境參數設置-XX：+HeapDumpOnOutOfMemoryError 參數，讓 JVM 碰到 OOM 場
景時輸出 dump 信息。 
說明：OOM 的發生是有概率的，甚至相隔數月才出現一例，出錯時的堆內信息對解決問題非常有幫助。 
6.【推薦】在線上生產環境，JVM 的 Xms 和 Xmx 設置一樣大小的內存容量，避免在 GC 后調整堆大小帶
來的壓力。 
7.【推薦】了解每個服務大致的平均耗時，可以通過獨立配置線程池，將較慢的服務與主線程池隔離開， 
免得不同服務的線程同歸於盡。 
8.【參考】服務器內部重定向必須使用 forward；外部部重定向地址必須使用 URL Broker 生成，否則因線
上採用 HTTPS 協議而導致瀏覽器提示“不安全”。此外，還會帶來 URL 維護不一致的問題。
Java 開發手冊（黃山版） 
 
 
40/51 
七、設計規約 
1.【強制】存儲方案和底層數據結構的設計獲得評審一致通過，並沉澱成為文檔。 
說明：有缺陷的底層數據結構容易導致系統風險上升，可擴展性下降，重構成本也會因歷史數據遷移和系統平滑過渡而
陡然增加，所以，存儲方案和數據結構需要認真地進行設計和評審，生產環境提交執行后，需要進行 double check。 
正例：評審內容包括存儲介質選型、表結構設計能否滿足技術方案、存取性能和存儲空間能否滿足業務發展、表或字段
之間的辯證關係、字段名稱、字段類型、索引等；數據結構變更（如在原有表中新增字段）也需要在評審通過後上線。 
2.【強制】在需求分析階段，如果與系統交互的 User 超過一類並且相關的 UseCase 超過 5 個，使用用例圖
來表達更加清晰的結構化需求。 
3.【強制】如果某個業務對象的狀態超過 3 個，使用狀態圖來表達並且明確狀態變化的各個觸發條件。 
說明：狀態圖的核心是對象狀態，首先明確對象有多少種狀態，然後明確兩兩狀態之間是否存在直接轉換關係，再明確
觸髮狀態轉換的條件是什麼。 
正例：淘寶訂單狀態有已下單、待付款、已付款、待發貨、已發貨、已收貨等。比如已下單與已收貨這兩種狀態之間是
不可能有直接轉換關係的。 
4.【強制】如果系統中某個功能的調用鏈路上的涉及對象超過 3 個，使用時序圖來表達並且明確各調用環
節的輸入與輸出。 
說明：時序圖反映了一系列對象間的交互與協作關係，清晰立體地反映系統的調用縱深鏈路。 
5.【強制】如果系統中模型類超過 5 個，且存在複雜的依賴關係，使用類圖來表達並且明確類之間的關係。 
說明：類圖像建築領域的施工圖，如果搭平房，可能不需要，但如果建造螞蟻 Z 空間大樓，肯定需要詳細的施工圖。 
6.【強制】如果系統中超過 2 個對象之間存在協作關係，並需要表示複雜的處理流程，使用活動圖來表示。 
說明：活動圖是流程圖的擴展，增加了能夠體現協作關係的對象泳道，支持表示併發等。 
7.【強制】系統設計時要準確識別出弱依賴，並針對性地設計降級和應急預案，保證核心繫統正常可用。 
說明：系統依賴的第三方服務被降級或屏蔽后，依然不會影響主幹流程繼續進行，僅影響信息展示、或消息通知等非關
鍵功能，那麼這些服務稱為弱依賴。 
正例：當系統弱依賴於多個外部服務時，如果下游服務耗時過長，則會嚴重影響當前調用者，必須採取相應降級措施，
比如，當調用鏈路中某個下游服務調用的平均響應時間或錯誤率超過閾值時，系統自動進行降級或熔斷操作，屏蔽弱依
賴負面影響，保護當前系統主幹功能可用。 
反例：某個疫情相關的二維碼出錯：“服務器開了點小差，請稍後重試”，不可用時長持續很久，引起社會高度關注，
原因可能為調用的外部依賴服務 RT 過高而導致系統假死，而在显示端沒有做降級預案，只能直接拋錯給用戶。 
8.【推薦】系統架構設計時明確以下目標： 
⚫ 確定系統邊界。確定系統在技術層面上的做與不做。 
⚫ 確定系統內模塊之間的關係。確定模塊之間的依賴關係及模塊的宏觀輸入與輸出。 
⚫ 確定指導後續設計與演化的原則。使後續的子系統或模塊設計在一個既定的框架內和技術方向上繼續演化。 
⚫ 確定非功能性需求。非功能性需求是指安全性、可用性、可擴展性等。 
9.【推薦】需求分析與系統設計在考慮主幹功能的同時，需要充分評估異常流程與業務邊界。 
10.【推薦】類在設計與實現時要符合單一原則。 
說明：單一原則最易理解卻是最難實現的一條規則，隨着系統演進，很多時候，忘記了類設計的初衷。 
11.【推薦】謹慎使用繼承的方式來進行擴展，優先使用聚合/組合的方式來實現。 
說明：不得已使用繼承的話，必須符合里氏代換原則，此原則說父類能夠出現的地方子類一定能夠出現，比如，“把
錢交出來”，錢的子類美元、歐元、人民幣等都可以出現。 
12.【推薦】系統設計階段，根據依賴倒置原則，盡量依賴抽象類與接口，有利於擴展與維護。 
說明：低層次模塊依賴於高層次模塊的抽象，方便系統間的解耦。 
Java 開發手冊（黃山版） 
 
 
41/51 
13.【推薦】系統設計階段，注意對擴展開放，對修改閉合。 
說明：極端情況下，交付的代碼是不可修改的，同一業務域內的需求變化，通過模塊或類的擴展來實現。 
14.【推薦】系統設計階段，共性業務或公共行為抽取出來公共模塊、公共配置、公共類、公共方法等，在
系統中不出現重複代碼的情況，即 DRY 原則（Don't Repeat Yourself）。 
 
說明：隨着代碼的重複次數不斷增加，維護成本指數級上升。隨意複製和粘貼代碼，必然會導致代碼的重複，在維護代
碼時，需要修改所有的副本，容易遺漏。必要時抽取共性方法，或者抽象公共類，甚至是組件化。 
 正例：一個類中有多個 public 方法，都需要進行數行相同的參數校驗操作，這個時候請抽取： 
private boolean checkParam(DTO dto) {...} 
15.【推薦】避免如下誤解：敏捷開發=講故事+編碼+發布。 
說明：敏捷開發是快速交付迭代可用的系統，省略多餘的設計方案，摒棄傳統的審批流程，但核心關鍵點上的必要設計
和文檔沉澱是需要的。 
反例：某團隊為了業務快速發展，敏捷成了產品經理催進度的借口，系統中均是勉強能運行但像麵條一樣的代碼，可
維護性和可擴展性極差，一年之後，不得不進行大規模重構，得不償失。 
16.【參考】設計文檔的作用是明確需求、理順邏輯、後期維護，次要目的用於指導編碼。 
 說明：避免為了設計而設計，系統設計文檔有助於後期的系統維護和重構，所以設計結果需要進行分類歸檔保存。 
17.【參考】可擴展性的本質是找到系統的變化點，並隔離變化點。 
 說明：世間眾多設計模式其實就是一種設計模式即隔離變化點的模式。 
 正例：極致擴展性的標誌，就是需求的新增，不會在原有代碼交付物上進行任何形式的修改。 
18.【參考】設計的本質就是識別和表達系統難點。 
說明：識別和表達完全是兩回事，很多人錯誤地認為識別到系統難點在哪裡，表達只是自然而然的事情，但是大家在
設計評審中經常出現語焉不詳，甚至是詞不達意的情況。準確地表達系統難點需要具備如下能力：表達規則和表達工
具的熟練性。抽象思維和總結能力的局限性。基礎知識體系的完備性。深入淺出的生動表達力。 
19.【參考】代碼即文檔的觀點是錯誤的，清晰的代碼只是文檔的某個片斷，而不是全部。 
說明：代碼的深度調用，模塊層面上的依賴關係網，業務場景邏輯，非功能性需求等問題要相應的文檔來完整地呈現。 
20.【參考】在做無障礙產品設計時，需要考慮到： 
⚫ 所有可交互的控件元素必須能被 tab 鍵聚焦，並且焦點順序需符合自然操作邏輯。 
⚫ 用於登錄校驗和請求攔截的驗證碼均需提供圖形驗證以外的其它方式。 
⚫ 自定義的控件類型需明確交互方式。 
正例：登錄場景中，輸入框的按鈕都需要考慮 tab 鍵聚焦，符合自然邏輯的操作順序如下，"輸入用戶名，輸入密碼，輸
入驗證碼，點擊登錄"，其中驗證碼實現語音驗證方式。如有自定義標籤實現的控件設置控件類型可使用 role 屬性。 
 
 
 
 
 
 
 
Java 開發手冊（黃山版） 
 
 
42/51 
附 1：版本歷史 
版本號 
版本名 
發布日期 
備註 
-- 
-- 
2016.12.07 
試讀版本首次對外發布 
1.0.0 
正式版 
2017.02.09 
阿里巴巴集團正式對外發布 
1.0.1 
-- 
2017.02.13 
1）修正 String[]的前後矛盾 
2）vm 修正成 velocity 
3）修正 countdown 描述錯誤 
1.0.2 
-- 
2017.02.20 
1）去除文底水印 
2）數據類型中引用太陽系年齡問題 
3）修正關於異常和方法簽名的部分描述 
4）修正 final 描述 
5）去除 Comparator 部分描述 
1.1.0 
-- 
2017.02.27 
1）增加前言 
2）增加<? extends T>描述和說明 
3）增加版本歷史 
4）增加專有名詞解釋 
1.1.1 
-- 
2017.03.31 
修正頁碼總數和部分示例 
1.2.0 
完美版 
2017.05.20 
1）根據云棲社區的“聚能聊”活動反饋，對手冊的頁碼、排版、描述進行修正 
2）增加 final 的適用場景描述 
3）增加關於鎖的粒度的說明 
4）增加“指定集合大小”的詳細說明以及正反例 
5）增加衛語句的示例代碼 
6）明確數據庫表示刪除概念的字段名為 is_deleted 
1.3.0 
終極版 
2017.09.25 
增加單元測試規約，阿里開源的 IDE 代碼規約檢測插件：點此下載 
1.3.1 
紀念版 
2017.11.30 
修正部分描述；採用和 P3C 開源 IDE 檢測插件相同的 Apache2.0 協議 
1.4.0 
詳盡版 
2018.05.20 
增加設計規約大類，共 16 條 
1.5.0 
華山版 
2019.06.19 
1）鑒於手冊是 Java 社區開發者集體智慧的結晶，移除《阿里巴巴 Java 開發手冊》
的限定詞“阿里巴巴” 
2）新增 21 條新規約。比如，switch 的 NPE 問題、浮點數的比較、無泛型限制、鎖
的使用方式、判斷表達式、日期格式等 
3）修改描述 112 處。比如，IFNULL 的判斷、集合的 toArray、日誌處理等 
4）完善若干處示例。比如，衛語句示例、enum 示例、finally 的 return 示例等 
1.6.0 
泰山版 
2020.04.22 
1）發布錯誤碼統一解決方案，詳細參考附表 3。 
2）修改描述 90 處。比如，阻塞等待鎖、建表的小數類型等。 
3）完善若干處示例。比如，ISNULL 的示例等。 
4）新增 34 條新規約。比如，日期時間的閏年、閏月問題，三目運算的自動拆箱，
SQL 查詢的表別名限定，Collectors 類的 toMap() 方法使用注意等。 
Java 開發手冊（黃山版） 
 
 
43/51 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
1.7.0 
嵩山版 
2020.08.03 
1）新增前後端規約 14 條。 
2）新增禁止任何歧視性用語的約定。 
3）新增涉及敏感操作的情況下日誌需要保存六個月的約定。 
4）修正 BigDecimal 類中關於 compareTo 和 equals 的等值比較。 
5）修正 HashMap 關於 1024 個元素擴容的次數。 
6）修正架構分層規範與相關說明。 
7）修正泰山版中部分格式錯誤和描述錯誤。 
1.7.1 
黃山版 
2022.02.03 
1）新增 11 條新規約。比如，浮點數的後綴統一為大寫；枚舉的屬性字段必須是私
有且不可變；配置文件中的密碼需要加密等。 
2）新增描述中的正反例 2 條。比如，多個構造方法次序、NoSuchMethodError 處
理；新增擴展說明 5 條。比如，父集合元素的增加或刪除異常等。 
3）修改描述 22 處。比如，魔法值的示例代碼、ScheduledThreadPool 問題等。 
4）修正嵩山版中部分代碼格式錯誤和描述錯誤。 
Java 開發手冊（黃山版） 
 
 
44/51 
附 2：專有名詞解釋 
1. 
POJO（Plain Ordinary Java Object）：在本規約中，POJO 專指只有 setter / getter / toString 的簡單類，包括  
DO / DTO / BO / VO 等。 
2. 
DO（Data Object）：阿里巴巴專指數據庫表一 一對應的 POJO 類。  此對象與數據庫表結構一 一對應，通過 DAO 層
向上傳輸數據源對象。 
3. 
PO（Persistent Object）：也指數據庫表一 一對應的 POJO 類。  此對象與數據庫表結構一 一對應，通過 DAO 層向上
傳輸數據源對象。 
4. 
DTO（Data Transfer Object ）：數據傳輸對象，Service 或 Manager 向外傳輸的對象。 
5. 
BO（Business Object）：業務對象，可以由 Service 層輸出的封裝業務邏輯的對象。 
6. 
Query：數據查詢對象，各層接收上層的查詢請求。注意超過 2 個參數的查詢封裝，禁止使用 Map 類來傳輸。 
7. 
VO（View Object）：显示層對象，通常是 Web 向模板渲染引擎層傳輸的對象。 
8. 
 CAS（Compare And Swap）  ：解決多線程并行情況下使用鎖造成性能損耗的一種機制，這是硬件實現的原子操作。
CAS 操作包含三個操作數：內存位置、預期原值和新值。如果內存位置的值與預期原值相匹配，那麼處理器會自動將該
位置值更新為新值。否則，處理器不做任何操作。 
9. 
GAV（GroupId、ArtifactId、Version）：Maven 坐標，是用來唯一標識 jar 包。 
10. OOP（Object Oriented Programming）：本文泛指類、對象的編程處理方式。 
11. AQS（AbstractQueuedSynchronizer）：利用先進先出隊列實現的底層同步工具類，它是很多上層同步實現類的基
礎，比如： ReentrantLock、CountDownLatch、  Semaphore 等，它們通過繼承 AQS 實現其模版方法，然後將 AQS
子類作為同步組件的內部類，通常命名為 Sync。 
12. ORM（Object Relation Mapping）：對象關係映射，對象領域模型與底層數據之間的轉換，本文泛指 iBATIS，
mybatis 等框架。 
13. NPE（java.lang.NullPointerException）：空指針異常。 
14. OOM（Out Of Memory）：源於 java.lang.OutOfMemoryError，當 JVM 沒有足夠的內存來為對象分配空間並且垃
圾回收器也無法回收空間時，系統出現的嚴重狀況。 
15. GMT（Greenwich Mean Time）：指位於英國倫敦郊區的皇家格林尼治天文台的標準時間，因為本初子午線被定義
在通過那裡的經線。地球每天的自轉是有些不規則的，而且正在緩慢減速，現在的標準時間是協調世界時（UTC），
它由原子鍾提供。 
16. 一方庫：本工程內部子項目模塊依賴的庫（jar 包）。 
17. 二方庫：公司內部發布到中央倉庫，可供公司內部其它應用依賴的庫（jar 包）。 
18. 三方庫：公司之外的開源庫（jar 包）。 
 
 
 
 
 
 
 
Java 開發手冊（黃山版） 
 
 
45/51 
附 3：錯誤碼列表 
錯誤碼 
中文描述 
說明 
00000 
一切 ok 
正確執行后的返回 
A0001 
用戶端錯誤 
一級宏觀錯誤碼 
A0100 
用戶註冊錯誤 
二級宏觀錯誤碼 
A0101 
用戶未同意隱私協議 
 
A0102 
註冊國家或地區受限 
 
A0110 
用戶名校驗失敗 
 
A0111 
用戶名已存在 
 
A0112 
用戶名包含敏感詞 
 
A0113 
用戶名包含特殊字符 
 
A0120 
密碼校驗失敗 
 
A0121 
密碼長度不夠 
 
A0122 
密碼強度不夠 
 
A0130 
校驗碼輸入錯誤 
 
A0131 
短信校驗碼輸入錯誤 
 
A0132 
郵件校驗碼輸入錯誤 
 
A0133 
語音校驗碼輸入錯誤 
 
A0140 
用戶證件異常 
 
A0141 
用戶證件類型未選擇 
 
A0142 
大陸身份證編號校驗非法 
 
A0143 
護照編號校驗非法 
 
A0144 
軍官證編號校驗非法 
 
A0150 
用戶基本信息校驗失敗 
 
A0151 
手機格式校驗失敗 
 
A0152 
地址格式校驗失敗 
 
A0153 
郵箱格式校驗失敗 
 
Java 開發手冊（黃山版） 
 
 
46/51 
A0200 
用戶登錄異常 
二級宏觀錯誤碼 
A0201 
用戶賬戶不存在 
 
A0202 
用戶賬戶被凍結 
 
A0203 
用戶賬戶已作廢 
 
A0210 
用戶密碼錯誤 
 
A0211 
用戶輸入密碼錯誤次數超限 
 
A0220 
用戶身份校驗失敗 
 
A0221 
用戶指紋識別失敗 
 
A0222 
用戶面容識別失敗 
 
A0223 
用戶未獲得第三方登錄授權 
 
A0230 
用戶登錄已過期 
 
A0240 
用戶驗證碼錯誤 
 
A0241 
用戶驗證碼嘗試次數超限 
 
A0300 
訪問權限異常 
二級宏觀錯誤碼 
A0301 
訪問未授權 
 
A0302 
正在授權中 
 
A0303 
用戶授權申請被拒絕 
 
A0310 
因訪問對象隱私設置被攔截 
 
A0311 
授權已過期 
 
A0312 
無權限使用 API  
 
A0320 
用戶訪問被攔截 
 
A0321 
黑名單用戶 
 
A0322 
賬號被凍結 
 
A0323 
非法 IP 地址 
 
A0324 
網關訪問受限 
 
A0325 
地域黑名單 
 
A0330 
服務已欠費 
 
Java 開發手冊（黃山版） 
 
 
47/51 
A0340 
用戶簽名異常 
 
A0341 
RSA 簽名錯誤 
 
A0400 
用戶請求參數錯誤 
二級宏觀錯誤碼 
A0401 
包含非法惡意跳轉鏈接 
 
A0402 
無效的用戶輸入 
 
A0410 
請求必填參數為空 
 
A0411 
用戶訂單號為空 
 
A0412 
訂購數量為空 
 
A0413 
缺少時間戳參數 
 
A0414 
非法的時間戳參數 
 
A0420 
請求參數值超出允許的範圍 
 
A0421 
參數格式不匹配 
 
A0422 
地址不在服務範圍 
 
A0423 
時間不在服務範圍 
 
A0424 
金額超出限制 
 
A0425 
數量超出限制 
 
A0426 
請求批量處理總個數超出限制 
 
A0427 
請求 JSON 解析失敗 
 
A0430 
用戶輸入內容非法 
 
A0431 
包含違禁敏感詞 
 
A0432 
圖片包含違禁信息 
 
A0433 
文件侵犯版權 
 
A0440 
用戶操作異常 
 
A0441 
用戶支付超時 
 
A0442 
確認訂單超時 
 
A0443 
訂單已關閉 
 
A0500 
用戶請求服務異常 
二級宏觀錯誤碼 
Java 開發手冊（黃山版） 
 
 
48/51 
A0501 
請求次數超出限制 
 
A0502 
請求併發數超出限制 
 
A0503 
用戶操作請等待 
 
A0504 
WebSocket 連接異常 
 
A0505 
WebSocket 連接斷開 
 
A0506 
用戶重複請求 
 
A0600 
用戶資源異常 
二級宏觀錯誤碼 
A0601 
賬戶餘額不足 
 
A0602 
用戶磁盤空間不足 
 
A0603 
用戶內存空間不足 
 
A0604 
用戶 OSS 容量不足 
 
A0605 
用戶配額已用光 
螞蟻森林澆水數或每天抽獎數 
A0700 
用戶上傳文件異常 
二級宏觀錯誤碼 
A0701 
用戶上傳文件類型不匹配 
 
A0702 
用戶上傳文件太大 
 
A0703 
用戶上傳圖片太大 
 
A0704 
用戶上傳視頻太大 
 
A0705 
用戶上傳壓縮文件太大 
 
A0800 
用戶當前版本異常 
二級宏觀錯誤碼 
A0801 
用戶安裝版本與系統不匹配 
 
A0802 
用戶安裝版本過低 
 
A0803 
用戶安裝版本過高 
 
A0804 
用戶安裝版本已過期 
 
A0805 
用戶 API 請求版本不匹配 
 
A0806 
用戶 API 請求版本過高 
 
A0807 
用戶 API 請求版本過低 
 
A0900 
用戶隱私未授權 
二級宏觀錯誤碼 
Java 開發手冊（黃山版） 
 
 
49/51 
A0901 
用戶隱私未簽署 
 
A0902 
用戶攝像頭未授權 
 
A0903 
用戶相機未授權 
 
A0904 
用戶圖片庫未授權 
 
A0905 
用戶文件未授權 
 
A0906 
用戶位置信息未授權 
 
A0907 
用戶通訊錄未授權 
 
A1000 
用戶設備異常 
二級宏觀錯誤碼 
A1001 
用戶相機異常 
 
A1002 
用戶麥克風異常 
 
A1003 
用戶聽筒異常 
 
A1004 
用戶揚聲器異常 
 
A1005 
用戶 GPS 定位異常 
 
 
B0001 
系統執行出錯 
一級宏觀錯誤碼 
B0100 
系統執行超時 
二級宏觀錯誤碼 
B0101 
系統訂單處理超時 
 
B0200 
系統容災功能被觸發 
二級宏觀錯誤碼 
B0210 
系統限流 
 
B0220 
系統功能降級 
 
B0300 
系統資源異常 
二級宏觀錯誤碼 
B0310 
系統資源耗盡 
 
B0311 
系統磁盤空間耗盡 
 
B0312 
系統內存耗盡 
 
B0313 
文件句柄耗盡 
 
B0314 
系統連接池耗盡 
 
B0315 
系統線程池耗盡 
 
Java 開發手冊（黃山版） 
 
 
50/51 
B0320 
系統資源訪問異常 
 
B0321 
系統讀取磁盤文件失敗 
 
 
C0001 
調用第三方服務出錯 
 
C0100 
中間件服務出錯 
一級宏觀錯誤碼 
C0110 
RPC 服務出錯 
二級宏觀錯誤碼 
C0111 
RPC 服務未找到 
 
C0112 
RPC 服務未註冊 
 
C0113 
接口不存在 
 
C0120 
消息服務出錯 
 
C0121 
消息投遞出錯 
 
C0122 
消息消費出錯 
 
C0123 
消息訂閱出錯 
 
C0124 
消息分組未查到 
 
C0130 
緩存服務出錯 
 
C0131 
key 長度超過限制 
 
C0132 
value 長度超過限制 
 
C0133 
存儲容量已滿 
 
C0134 
不支持的數據格式 
 
C0140 
配置服務出錯 
 
C0150 
網絡資源服務出錯 
 
C0151 
VPN 服務出錯 
 
C0152 
CDN 服務出錯 
 
C0153 
域名解析服務出錯 
 
C0154 
網關服務出錯 
 
C0200 
第三方系統執行超時 
二級宏觀錯誤碼 
C0210 
RPC 執行超時 
 
Java 開發手冊（黃山版） 
 
 
51/51 
C0220 
消息投遞超時 
 
C0230 
緩存服務超時 
 
C0240 
配置服務超時 
 
C0250 
數據庫服務超時 
 
C0300 
數據庫服務出錯 
二級宏觀錯誤碼 
C0311 
表不存在 
 
C0312 
列不存在 
 
C0321 
多表關聯中存在多個相同名稱的列 
 
C0331 
數據庫死鎖 
 
C0341 
主鍵衝突 
 
C0400 
第三方容災系統被觸發 
二級宏觀錯誤碼 
C0401 
第三方系統限流 
 
C0402 
第三方功能降級 
 
C0500 
通知服務出錯 
二級宏觀錯誤碼 
C0501 
短信提醒服務失敗 
 
C0502 
語音提醒服務失敗 
 
C0503 
郵件提醒服務失敗 
 
Java 開發手冊（黃山版） 
 
 
 
 
