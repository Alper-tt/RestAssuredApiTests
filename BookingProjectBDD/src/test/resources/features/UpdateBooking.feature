Feature: Kullanici rezervasyon detaylarini degistirsin
  Scenario: Kullanici rezervasyon id ile rezervasyon detaylarini degistirsin
    Given Kullanici rezervasyon icin giris bilgilerini girsin
    When Kullanici rezervasyonu olustursun
    Then Rezervasyon basarili sekilde olusturulsun
    When Kullanici rezervasyon id ile rezervasyon bilgilerini degistirsin
    Then Rezervasyon id ile basarili bir sekilde goruntulensin
    And Degistirilen rezervasyon bilgileri kontrol edilsin