Feature: Kullanici rezervasyon detaylarini degistirsin
  Scenario: Kullanici rezervasyon id ile rezervasyon detaylarini degistirsin
    Given Kullanici rezervasyon icin giris bilgilerini girsin
    When Kullanici rezervasyonu olustursun
    Then Rezervasyon basarili sekilde olusturulsun
    Then Kullanici rezervasyon id ile rezervasyon bilgilerini degistirsin
    And Rezervasyon id ile basarili bir sekilde goruntulensin
    Then Degistirilen rezervasyon bilgileri kontrol edilsin