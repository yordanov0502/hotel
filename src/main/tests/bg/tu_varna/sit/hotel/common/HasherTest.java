package bg.tu_varna.sit.hotel.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HasherTest {
    @Test
    void TestSHA512()
    {
        assertEquals("259a26d4f67a1a55bd8f31291f6b7c84bc4e559c41d0b8e4be1679a9635725ac18f8c344a316872c43fa46e67cd787b27d67d1446905b4507443b3b248cedf03",Hasher.SHA512.hash("B0502HT"));
        assertEquals("4049063fae7c5a2e94da83f375a0fc8c26d94893d2f5f70fa02d317c56cf58f18dd47bfb8bd9554d98b03b416b3b4c5c3bb6115d72e4b1edaec082e167d5366a",Hasher.SHA512.hash("rI@_p-13@#$%^&+=*BuLgArIa"));
        assertEquals("8abded1cbadd6b0a4169efd41edb82dd152e4934c1c740d3e6a39ee6271e6cf2c1c118f115d0027d3eea73b937b0cc9fd282cdc036af1d13181005fb1f49d746",Hasher.SHA512.hash("95v192658266156579006531V8m89218498Y2666428W4057T8377882024D053759170181c9651908364985b5157c24kW12Q00653047287$^m8N4632757834794664E1848983500966129357868049833810562R289497072U6252336V924M1161108900311g4443Jn085i6~236678542c0314078n258112934459823617515059845352667#619354040k6449006&736870224174487572224925945324M6$9509216933269c510R5892436832x718037w36&9313o52v70067Kr4001666609G565h604027989P80335977674m09645b54357213519903110717114896$62064904761p1375650V633141693B42901W5938476505y38514S8041l2583989236618877034188240293102410270795u990623619147GG353K259R8702K552h0952812144x45032z156854830cF77588507690F874945656C1370V5443C2101#5448668017441332068802662M7594x23410H98516@58914148025L47268j87263017268N92159F101043050445DG07589558621961072496)535908711817y372`003019372068960103884223406(m6930241d15037102580196e8137170bk470500327615774229963325`73373413E87F76316x4869847940@8138813005J9&26308935754988E7982294!18277904525I7Z086122v6763922952n8219872X381116914689429D7u369151685t7w205S060063581257450540j606724518880027788478324577r996517c01n972030410961698075327368b7337531686564039026V6%4316615P39854828666447709807550977276355755252f93388305785528f746443793684v600x1E6J8431140358789(79684U8369*218Sg078P4895u323569191378687qP301358039271059L799260752780754907526810057171z2254882g3835396516348037%8579Co1056S527704~7606870635174182r228349714846MR56649057428j8895793344G5694159657545B88633548634137467743605803312Z2Cah8023Z48S0Z361520p966223186791d2306887796699912889764g661487787U6m9783558$290589694888267^s4$78710u602463R2`3f088338661f50f9563441*0j5617Z6v6v61273756729103066174580034p889J3G3729888041397631765280041966621388277219365D8449@g2340018I8~0816453254I0253193252482992151849921769183527292o4u827h65246397358207875364557115682875558h612283749720932279501467Bd%1729#17929u3t40041s705319056E7^@10T556626406197319W26610602376035082949873w80y61422A652158Kv0PJ6173434243$52386216V30J247370055574520351516y75j81560858338S71006193843134l428599K29w5o542t3193658n28f3495154877059260540826933a793302525p3956769n03u59185699W5020&0u84671367430U208979a4557725877416027222851925W0862451419(3725872361q993390612475265946375233555966q7822741195497487j58P733^558544333835375394658L45d8821181O8V3582751228v24936603634642504734727860146x316X985237242Hd949A49F56076315928202215484642784568150%51012294w0467842331185824369708274259282639283V5932430J0720664837883345099402392926911060O2748820157118043P1Cz5961233325f@77030098539253Z1201860314599160D32609755175#7I925023090816D875560818023584V8111195915906C0426658728912z965O01685934584170089524x3040B17477w684240145427521N6M179664363w741098r87070180799316n30256490787015&7694382239290685154941667006235951m4547254I12S1t09330648517600411686q57151Ej4846201673t4250815272530125952R7W14660H920!0781796TS261295296068y062!3236259264368246O730948w228755887265Q92680635727545261015409P461463@6220E9984681085794784~23407202a343135436084246205240915206708927978463426K0953387176V3447626105720#98874623406084b0d08J8217755u77092076255307837901506094730220100450377789852560S858027078l918g14207246373357f26971Y6Y155a683322832854092754I2R35829766930795w777j010043tc9403305119450759459067510694P451877929h737605o4R7j05183240T10010010409814282067349616567967206046818879440800r983920030270H6808~644436^5337341350b4414t8249181q1541118505149446c29924872B11689402017164388307439653508T!96190166971K28D3035016126m44796138014944673311578W74716617113062408)%%3867167U6772414169u4353866130026669w9&942v801432718k00`24572q723964404398J881489923546913L07335668290`V263683281499351H13l638$583681279329332377571274965H294607981997Z3p1S9428Y04417f314ZCU702589M41093312601)940224357202n60375290)8y21L8067141748593921xG872705m4221779964T44169870196977538142309969193037Z93235914^122598S0Z0196640461M394029w780258064459868642104335w7286242303622869592G869293`330364237v2663813991830180404z480805W4V443728E8849164886z56`873117984W98431643627087779a8N611258510715344726562249P48o06059376163997k28206@9w10512T610771A740505057G83684295P58115(108533458776l873c9198946(91S149892051Ykm64307224372480p08B0U&03807791U44422#1279979710730910U45123c0829543004028f8540165F532K150812814991525612010V306391104441846T7323186238426619143808565W0408147WY8071753M930659217794472502667046836353508400066796942163161n966695116p5259H78035669589502701201332250835153Y4w2x2376454703360&9sr2011u807P83789449529k8607963256405j3917975605W34865459835562$240s1138328870217^Z72oy3464998527666A0745814550757397523476032997544064514419493i105835287c618a17n01239g00217159451496%68403264085003c94m089082D3038u87N5451349720623750N768193201535688259909501366466w621515935819476788873316596210464MZ22839Jk5x08C7Q2305E26182160a97464330M8316340574B8649218817979f0%49863965603955G4w0v74973559995533607P36`668#7797167x394^72565404151!45268735103471973821538125)6293558975690631oR9002372058279531806165754B02u2737066876961r041291621399042916104o3833439820y0102679629702u7184247i4482136019dO47J07nP114165r51869636i892840783946u846z98621748858885071766339964849981j0002739L9(742l39197Z46844774k45709595315J47U42423135039882973J5697V558420`34898546478161286355383285875502810971u36A05015704390U85166wy5112l07L73765355E36N540y7797021147J8m416805U3d93l574248D29463521558072475Wo8F93162057l7689712418657406w163&9549672C0470a47704806682l913129b55853048849~3P6#4411005703938u6181240281814716893837469408R608222572998842641035310117186693569660J63094307417734466929773852732814792330265~S702669270501c2238#5280281727947471IB9862029298517a87987071902083056647225817q*e196F5917!2%783434874q59i266943j8842$04*997@348043823577806565638aA05659l156709422860292196743457G29711536591442502595870977@45iw98241890423388516V773224182756121835HpX241253251574741425820755SN534831530851023811483750r72794192091497203700)7838c07536991280301774313822594281660449484EI664V90201270!543008g4c24i370073780610333866985y207552909048672167409205160892242393V9#3Z9051749C`6#254664387N6523409404992578072ev1307635958172805137840916514032171Tz38499881154248954094i3461802Y02754269420541X699"));
    }
}