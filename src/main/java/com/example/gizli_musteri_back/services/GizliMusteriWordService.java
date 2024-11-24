package com.example.gizli_musteri_back.services;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import com.example.gizli_musteri_back.entities.GizliMusteri;
import com.example.gizli_musteri_back.repositories.GizliMusteriRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;




@Service
public class GizliMusteriWordService {

    private final GizliMusteriRepository gizliMusteriRepository;

    public GizliMusteriWordService(GizliMusteriRepository gizliMusteriRepository) {
        this.gizliMusteriRepository = gizliMusteriRepository;
    }

    public File generateWordAndZipFiles(LocalDate startDate, LocalDate endDate) throws IOException {
        List<GizliMusteri> musteriList = gizliMusteriRepository.findByKaydolmaTarihiBetween(startDate, endDate);
        
        // Geçici bir dizin oluşturma
        Path tempDir = Files.createTempDirectory("gizliMusteriDocs");
        
        for (GizliMusteri musteri : musteriList) {
            String fileName = musteri.getIsim() + "_" + musteri.getSoyad() + "_" + UUID.randomUUID() + ".docx";
            File wordFile = new File(tempDir.toFile(), fileName);
            createWordDocument(musteri, wordFile);
        }
        
        // Zip dosyasını oluşturma
        File zipFile = new File(tempDir.toFile(), "GizliMusteriSozlesmeleri.zip");
        zipFiles(tempDir, zipFile);
        
        return zipFile;
    }

    private void createWordDocument(GizliMusteri musteri, File file) throws IOException {
        try (XWPFDocument document = new XWPFDocument();
             FileOutputStream out = new FileOutputStream(file)) {
            
            String gizliMusteriSozlesmeMetni = """      
    X Audit’in projelerinde görev alacak tüm Gizli Müşteriler, aşağıda detaylandırılan Etik Davranış Kuralları’na uyacaklarını beyan ve taahhüt ederler:
    X Audit’in projelerinde görev alacak tüm Gizli Müşteriler, aşağıda detaylandırılan Etik Davranış Kuralları’na uyacaklarını beyan ve taahhüt ederler:
    X Audit’de Gizli Müşteri olarak üstlendiğim görevlerde; Tüm ziyaretleri elimden gelen en iyi şekilde, doğruluk ve dürüstlük ilkeleri çerçevesinde gerçekleştireceğimi, Gizli Müşteri çalışmaları ile ilgili olarak X Audit tarafından uygulanacak ve projeler öncesinde katılmam istenecek her türlü eğitim ve sınava katılacağımı, bu sınavlardan olumlu sonuçlar aldığım takdirde projelerde görev alabileceğimi, Gizli Müşteri çalışmaları hakkındaki genel ve proje bazındaki eğitimleri dikkatle dinleyeceğimi / izleyeceğimi, görevlendirileceğim projeye başlamadan önce tarafıma iletilen eğitim malzemelerini eksiksiz olarak okuyacağımı, anlamadığım bir şey olursa bunu netleştirmeden işe başlamayacağımı, eğitim almadan yapacağım ziyaretlerin geçerli sayılmayacağını, Eğitim bilgilendirmeleri sırasında bana verilmiş olan sözlü ve yazılı bilgileri X Audit’in izni olmadan üçüncü şahıslarla paylaşmayacağımı, eğitimler sırasında dağıtılan dokümanlardan hiçbir ortamda hiçbir kopya oluşturmayacağımı, Benden yazılı ya da sözlü olarak istenmiş olan her şeyin bir nedeni olduğunu kabul ederek, mantıksız bulduğum konular da dahil olmak üzere verilen direktiflerin aksi hiçbir davranışta bulunmayacağımı, Proje başlangıcında ya da sırasında tarafıma iletilecek zaman planına birebir uyacağımı, ziyaretleri bu plan doğrultusunda gerçekleştireceğimi ve formlarımı proje yetkililerine zamanında ya da zamanından önce ulaştıracağımı, Tüm gizlilik anlaşmalarına uyacağımı, Herhangi bir nedenden dolayı bir ziyareti gerçekleştiremeyeceğim durumlarda X Audit’i anında haberdar edeceğimi, X Audit yetkililerinin bana ulaşabilmesi için saha çalışması sırasında cep telefonumu sürekli olarak açık tutacağımı, cep telefonuyla ulaşılamadığım durumlarda proje yetkililerini e-posta, normal telefon, faks, vb. araçlarla düzenli olarak haberdar edeceğimi, Çalışma sırasında topladığım dokümanların hepsini X Audit’e göndereceğimi, tutmuş olduğum notları, müşterilerden gelebilecek soruları cevaplayabilmek amacıyla en az 60 gün boyunca saklayacağımı, Başka firmalar için Gizli Müşteri olarak çalışan kişilerin, imzaladıkları gizlilik anlaşmalarını ihlal etmelerini istemeyeceğimi ve onları buna teşvik etmeyeceğimi, Herhangi bir yayın organında X Audit, müşteriler veya gizli müşteriler aleyhinde şikayette bulunmayacağımı, X Audit’in hangi müşteriler için gizli müşteri hizmeti verdiği hakkında hiç kimseyle bilgi paylaşmayacağımı, Gizli müşteri projelerinde ödenen ücretler ve karşılanan giderlerle ilgili hiç kimseyle bilgi paylaşmayacağımı, Gizli Müşteri olarak yaptığım ziyaretler hakkında birinci dereceden yakınlarım da dahil olmak üzere kimseye bilgi vermeyeceğimi, firma adı belirtmeyeceğimi, gözlemlerimi ve tecrübelerimi kimse ile paylaşmayacağımı, Gizli müşteri ziyaretleri sırasında, alkol, uyuşturucu gibi zararlı maddeler ile gözlem ve denetleme yeteneklerimi kısıtlayabilecek niteliklere sahip hiçbir reçeteli/reçetesiz ilacı kullanmayacağımı, X Audit’in onayı olmadan hiçbir müşteriyle doğrudan irtibata geçmeyeceğimi, Gerçekleştireceğim ziyaretler sırasında denetlediğim noktadaki işlem süreçlerindeki normal iş akışlarını bozmayacağımı, Gerçekleştireceğim herhangi bir Gizli Müşteri ziyareti sırasında tarafıma Gizli Müşteri olup olmadığım sorulduğu takdirde konudan tamamen habersiz davranarak kimliğimin ortaya çıkmamasını sağlayacağımı, X Audit tarafından aksi belirtilmedikçe Gizli Müşteri kimliğimi denetlediğim noktalarda açıklamayacağımı, Benim, ailemin ya da yakın arkadaşlarımın geçmişte ya da halihazırda çalıştığı firmalar nezdinde yürütülecek Gizli Müşteri projelerinde görev almayacağımı, Çalışma sırasında gerçekleştireceğim giderler konusunda, proje başlangıcında tarafıma iletilecek bütçe dışında hiçbir talepte bulunmayacağımı ve giderlerimle ilgili her türlü belgeyi istendiği takdirde X Audit’e ibraz edeceğimi, X Audit’in bilgisi ve onayı olmadan bana verilen işleri başkasına devretmeyeceğimi, kendi yerime başka kimseyi çalıştırmayacağımı, X Audit tarafından aksi bildirilmedikçe tüm ziyaretleri tek başıma yapacağımı, Dolduracağım formlardaki bilgilerin tarafımca yapılan gerçek gözlemlere ve/veya görüşmelere dayanan doğru bilgiler olacağını, X Audit’e vermiş olduğum adres, telefon ve diğer kişisel bilgilerin doğru olduğunu, bu bilgilerde herhangi bir değişiklik olduğunda X Audit’i derhal haberdar edeceğimi, X Audit tarafından atandığım bir projede görev aldığım süre boyunca eş zamanlı olarak, başka bir şirkette, aynı sektörde hizmet veren başka bir firmanın benzer bir işinde çalışmayacağımı, Benden beklenenleri tam olarak yerine getirmeden, ziyaret formlarını eksiksiz olarak teslim etmeden ve kontrol sonuçları alınmadan herhangi bir ödeme talep etmeyeceğimi önceden taahhüt ederim. Bu belgeyi onaylayarak, bu sözleşme çerçevesinde detaylandırılan dürüstlük, profesyonellik, hakkaniyet ve gizlilik ilkelerini okuduğumu, anladığımı ve uygulamayı kabul ettiğimi, sözleşmeyi ihlal eden herhangi bir davranışta bulunduğum takdirde X Audit’den hiçbir alacak talep etmeyeceğimi kabul ederim.
    """;


            
            String gizliMusteriKabulMetni = """
Yürüteceğim gizli müşteri çalışması esnasında, gizli müşteri olarak gideceğim işletmenin genel alanlarında kaydedilecek güvenlik kameraları görüntülerimin ve işletme yetkilileri ile işletme telefonları üzerinden gerçekleştirdiğim telefon görüşmelerine ait ses kayıtlarının paylaşılmasına ve veritabanına kaydetmiş olduğum tüm iletişim kanalları üzerinden benimle iletişime geçilmesine açık rıza gösterdiğimi kabul ve beyan ederim.
1.a) Kişisel Verilerin Elde Edilme Yöntemleri ve Hukuki Sebepleri Kişisel verileriniz, elektronik veya fiziki ortamda toplanmaktadır. İşbu Aydınlatma Metni'nde belirtilen hukuki sebeplerle toplanan kişisel verileriniz Kanun'un 5. ve 6. maddelerinde belirtilen kişisel veri işleme şartları çerçevesinde işlenebilmekte ve paylaşılabilmektedir. 1.b) Kişisel Verilerin İşleme Amaçları Kişisel verileriniz, Kanun'un 5. ve 6. maddelerinde belirtilen kişisel veri işleme şartları çerçevesinde X Audit tarafından sunulan ürün ve hizmetlerin ilgili kişilerin beğeni, kullanım alışkanlıkları, profil bilgileri ve ihtiyaçlarına göre özelleştirilerek ilgili kişilere önerilmesi, teklif edilmesi ve tanıtılması için gerekli olan aktivitelerin planlanması ve icrası, X Audit tarafından sunulan ürün ve hizmetlerden ilgili kişileri faydalandırmak için gerekli çalışmaların iş birimleri tarafından yapılması ve ilgili iş süreçlerinin yürütülmesi, X Audit tarafından yürütülen ticari faaliyetlerin gerçekleştirilmesi için gerekli çalışmaların yapılması ve buna bağlı iş süreçlerinin yürütülmesi, X Audit'in ticari ve/veya iş stratejilerinin planlanması ve icrası ve X Audit'in ve X Audit ile iş ilişkisi içerisinde olan ilgili kişilerin hukuki, teknik ve ticari-iş güvenliğinin temini amaçlarıyla işlenmektedir. 1.c) Kişisel Verilerin Paylaşılabileceği Taraflar ve Paylaşım Amaçları Kişisel verileriniz, Kanun'un 8. ve 9. maddelerinde belirtilen kişisel veri işleme şartları ve amaçları çerçevesinde, X Audit tarafından sunulan ürün ve hizmetlerin ilgili kişilerin beğeni, kullanım alışkanlıkları ve ihtiyaçlarına göre özelleştirilerek ilgili kişilere önerilmesi, teklif edilmesi ve tanıtılması için gerekli olan aktivitelerin planlanması ve icrası, X Audit tarafından sunulan ürün ve hizmetlerden ilgili kişileri faydalandırmak için gerekli çalışmaların yapılması ve iş süreçlerinin yürütülmesi, X Audit tarafından yürütülen ticari faaliyetlerin gerçekleştirilmesi için gerekli çalışmaların yapılması ve buna bağlı iş süreçlerinin yürütülmesi, X Audit'in ticari ve/veya iş stratejilerinin planlanması ve icrası ve X Audit'in ve iş ilişkisi içerisinde olduğu kişilerin hukuki, teknik ve ticari-iş güvenliğinin temini amaçlarıyla paylaşılabilecektir.
    """;
            
            // Başlık ekleme
            addParagraph(document, "X Audit Gizli Müşteri Sözleşmesi", true, 14, ParagraphAlignment.CENTER);
            
            // Gizli müşteri sözleşme metni
            addParagraph(document, gizliMusteriSozlesmeMetni, false, 11, ParagraphAlignment.LEFT);

            // Onay metni
            addParagraph(document, "“Yukarıdaki X Audit Gizli Müşteri Sözleşmesi'ni okudum, anladım ve kurallara uyacağımı kendi rızamla taahhüt ettim.”", false, 11, ParagraphAlignment.LEFT);

            // Boş kutu ekleme (Yüksekliği ayarlamak için tablo kullanma)
            addEmptyBox(document);

            addSpaceBetween(document,2); // 2 satır bosluk
            
            // Başlık ekleme
            addParagraph(document, "X Audit Gizli Müşteri Kabul Metni", true, 14, ParagraphAlignment.CENTER);
            
            addParagraph(document, gizliMusteriKabulMetni, false, 11, ParagraphAlignment.LEFT);
            
            // Onay metni
            addParagraph(document, "“Yukarıdaki X Audit Gizli Müşteri Kabul Metni'ni okudum, anladım ve kurallara uyacağımı kendi rızamla taahhüt ettim.”", false, 11, ParagraphAlignment.LEFT);
 
            // Boş kutu ekleme (Yüksekliği ayarlamak için tablo kullanma)
            addEmptyBox(document);
            
            addSpaceBetween(document,2); // 2 satır bosluk
            
            addParagraph(document, "Personel Bilgileri", true, 14, ParagraphAlignment.CENTER);
            
            addSpaceBetween(document, 1); // Örnek: 1 satır boşluk ekle
            
            String fotografDizini = "uploads/gizliMusteriFotograflari/" + musteri.getFotografDosyaAdi();
            addImageToWordDocument(document, fotografDizini, 200, 200, ParagraphAlignment.CENTER);

            addSpaceBetween(document, 1); // Örnek: 1 satır boşluk ekle
            
            // Tarihi gg.aa.yyyy formatında yazdırmak
            LocalDate dogumTarihi = musteri.getDogumTarihi();
            DateTimeFormatter formatterDogumTarihi = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String formattedDogumTarihi = dogumTarihi.format(formatterDogumTarihi);

            
            // Gizli müşteri kişisel bilgileri
            addSectionHeader(document, "Kişisel Bilgiler");
            addParagraph(document, "İsim: " + musteri.getIsim(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "Soyad: " + musteri.getSoyad(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "Doğum Yeri: " + musteri.getDogumYeri(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "Doğum Tarihi: " + formattedDogumTarihi, false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "Medeni Durum: " + musteri.getMedeniDurum(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "18 Yaşından Küçük Çocuk Sayısı: " + musteri.getCocukSayisi(), false, 11, ParagraphAlignment.LEFT);
            
            // İletişim bilgileri
            addSectionHeader(document, "İletişim Bilgileri");
            addParagraph(document, "Açık Adres: " + musteri.getAcikAdres(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "Semt: " + musteri.getSemt(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "İl: " + musteri.getIl(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "İlçe: " + musteri.getIlce(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "Ev Telefonu: " + musteri.getEvTelefonu(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "İş Telefonu: " + musteri.getIsTelefonu(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "Mobil Telefon: " + musteri.getMobilTelefon(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "E-mail: " + musteri.getEmail(), false, 11, ParagraphAlignment.LEFT);

            // Eğitim ve iş bilgileri
            addSectionHeader(document, "Eğitim ve İş Bilgileri");
            addParagraph(document, "Eğitim Durumu: " + musteri.getEgitimDurumu(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "Bildiğiniz Yabancı Diller: " + musteri.getBildigiYabanciDiller(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "Üniversite Bilgileri: " + musteri.getUniversiteBilgileri(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "Çalışma Durumu: " + musteri.getCalismaDurumu(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "Çalıştığı Sektör: " + musteri.getCalistigiSektor(), false, 11, ParagraphAlignment.LEFT);

            // Diğer özel bilgiler
            addSectionHeader(document, "Özel Bilgiler");
            addParagraph(document, "Boy-Beden Bilgileri: " + musteri.getBoyBedenBilgileri(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "Gözlük Kullanıyor musunuz? : " + musteri.getGozlukKullaniyorMu(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "Lens Kullanıyor musunuz? : " + musteri.getLensKullaniyorMu(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "Sigara Kullanıyor musunuz? : " + musteri.getSigaraKullaniyorMu(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "Ehliyetiniz var mı? : " + musteri.getEhliyetVarMi(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "Aktif Olarak Araç Kullanıyor musunuz? : " + musteri.getAktifAracKullaniyorMu(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "Otomobiliniz var mı? : " + musteri.getOtomobilVarMi(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "Çekirdek aile geliriniz ne kadar? : " + musteri.getCekirdekAileGeliri(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "Hobileriniz nedir? : " + musteri.getHobiler(), false, 11, ParagraphAlignment.LEFT);

            addSectionHeader(document, "Çalışma Bilgileri");
            addParagraph(document, "Daha önce gizli müşteri olarak görev aldınız mı?(Kaç kez): " + musteri.getDahaOnceGizliMusteri(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "Çalışabileceğiniz zaman dilimi: " + musteri.getCalisabilecegiZamanDilimi(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "Çalışabileceğiniz bölgeler: " + musteri.getCalisabilecegiBolgeler(), false, 11, ParagraphAlignment.LEFT);
            
            addSectionHeader(document, "Alıcı Bilgileri - Banka Bilgileri");
            addParagraph(document, "Tam Alıcı Adı: " + musteri.getTamAliciAdi(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "IBAN: " + musteri.getIban(), false, 11, ParagraphAlignment.LEFT);
            addParagraph(document, "Kimlik Numarası: " + musteri.getKimlikNumarasi(), false, 11, ParagraphAlignment.LEFT);

         // Tarihi gg.aa.yyyy formatında yazdırmak
            LocalDate kaydolmaTarihi = musteri.getKaydolmaTarihi();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String formattedDate = kaydolmaTarihi.format(formatter);

            
            addSpaceBetween(document,1); // 1 satır bosluk
            addParagraph(document, "Dijital Sisteme Kaydolunan Tarih: " + formattedDate, false, 11, ParagraphAlignment.LEFT);
            
            // Onay metni
            addParagraph(document, "“Yukarıdaki Personel Bilgilerini kendi rızamla dijital ortamda belirtilen tarihte doldurduğumu ve doğruluğunu kabul ederim.”", false, 11, ParagraphAlignment.LEFT);
            // Boş kutu ekleme (Yüksekliği ayarlamak için tablo kullanma)
            addEmptyBox(document);
            


            document.write(out);
        }
    }

    private void addParagraph(XWPFDocument document, String text, boolean bold, int fontSize, ParagraphAlignment alignment) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(alignment); // Paragrafın hizasını ayarlama
        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setFontSize(fontSize);
        if (bold) {
            run.setBold(true);
        }
    }

    private void addSpaceBetween(XWPFDocument document, int numberOfSpaces) {
        for (int i = 0; i < numberOfSpaces; i++) {
            document.createParagraph(); // Belirtilen sayıda boş paragraf ekler
        }
    }

    private void addSectionHeader(XWPFDocument document, String headerText) {
        XWPFParagraph headerParagraph = document.createParagraph();
        headerParagraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun headerRun = headerParagraph.createRun();
        headerRun.setText(headerText);
        headerRun.setBold(true);
        headerRun.setFontSize(12);
    }

    private void addEmptyBox(XWPFDocument document) {
        XWPFTable table = document.createTable(1, 1); // 1 satır, 1 sütun

        // Kenar ayarları
        CTTblBorders borders = table.getCTTbl().getTblPr().addNewTblBorders(); // Kenarları ekliyoruz

        CTBorder border = borders.addNewBottom(); // Alt kenar
        border.setVal(STBorder.SINGLE); // Düz çizgi
        border.setSz(BigInteger.valueOf(4)); // Kalınlık

        border = borders.addNewTop(); // Üst kenar
        border.setVal(STBorder.SINGLE); // Düz çizgi
        border.setSz(BigInteger.valueOf(4)); // Kalınlık

        border = borders.addNewLeft(); // Sol kenar
        border.setVal(STBorder.SINGLE); // Düz çizgi
        border.setSz(BigInteger.valueOf(4)); // Kalınlık

        border = borders.addNewRight(); // Sağ kenar
        border.setVal(STBorder.SINGLE); // Düz çizgi
        border.setSz(BigInteger.valueOf(4)); // Kalınlık

        // Kutu yüksekliğini ayarlama
        XWPFTableRow row = table.getRow(0);
        row.setHeight(950); // Yüksekliği artırma

        // Kutu içini boş bırak
        XWPFTableCell cell = row.getCell(0);
        cell.setText(""); // Kutunun içi boş

        // Hücre genişliğini ayarlama 
        table.setWidth("9250"); // Genişliği ayarlama
    }
    
	/*
	 * private void addPageBreak(XWPFDocument document) { // Yeni bir sayfa eklemek
	 * için boş bir paragraf oluşturuyoruz XWPFParagraph paragraph =
	 * document.createParagraph(); paragraph.createRun().addBreak(BreakType.PAGE);
	 * // Sayfa kesmesi ekliyoruz }
	 * 
	 */

    private void zipFiles(Path sourceDir, File zipFile) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            Files.walk(sourceDir).filter(path -> !Files.isDirectory(path)).forEach(path -> {
                ZipEntry zipEntry = new ZipEntry(sourceDir.relativize(path).toString());
                try {
                    zos.putNextEntry(zipEntry);
                    Files.copy(path, zos);
                    zos.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    
    public void addImageToWordDocument(XWPFDocument document, String imagePath, int width, int height, ParagraphAlignment alignment) throws IOException {
        // Yeni bir paragraf oluşturuyoruz
        XWPFParagraph paragraph = document.createParagraph();
        
        // Paragrafın hizalamasını ayarlıyoruz
        paragraph.setAlignment(alignment);
        
        // Bir dosya akışı açarak görüntüyü dosya sisteminden okuyoruz
        try (FileInputStream imageInputStream = new FileInputStream(imagePath)) {
            XWPFRun run = paragraph.createRun();

            // Görseli word dosyasına ekliyoruz. Ölçeklendirme birimleri için POI'nin Units sınıfını kullanıyoruz.
            run.addPicture(imageInputStream, XWPFDocument.PICTURE_TYPE_JPEG, imagePath, Units.toEMU(width), Units.toEMU(height));
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Görsel eklenirken bir hata oluştu: " + e.getMessage());
        }
    
    
    
}
}