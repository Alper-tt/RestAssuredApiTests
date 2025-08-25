Feature: Kullanici rezervasyonunu duzenlesin
  Scenario: Kullanici rezervasyonunun bir kismini duzenlesin
    Given Kullanici rezervasyon icin giris bilgilerini girsin
    When Kullanici rezervasyonu olustursun
    Then Rezervasyon basarili sekilde olusturulsun
    Then Kullanici rezervasyon id ile rezervasyonunun bir kismini degistirsin
    And Rezervasyon id ile basarili bir sekilde goruntulensin
    Then Degistirilen rezervasyon bilgileri kontrol edilsin
