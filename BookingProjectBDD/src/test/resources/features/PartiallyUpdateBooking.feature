Feature: Kullanici rezervasyonunu duzenlesin
  Scenario: Kullanici rezervasyonunun bir kismini duzenlesin
    Given Kullanici rezervasyon icin giris bilgilerini girsin
    When Kullanici rezervasyonu olustursun
    Then Rezervasyon basarili sekilde olusturulsun
    When Kullanici rezervasyon id ile rezervasyonunun bir kismini degistirsin
    Then Rezervasyon id ile basarili bir sekilde goruntulensin
    And Degistirilen rezervasyon bilgileri kontrol edilsin
