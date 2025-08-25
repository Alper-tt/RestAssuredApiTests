Feature: Rezervasyon id ile rezervasyon bulunsun
  Scenario: Rezervasyon id ile rezervasyon bilgileri goruntulensin
    Given Kullanici yeni bir rezervasyon olustursun
    And Kullanici rezervasyon icin giris bilgilerini girsin
    When Kullanici rezervasyonu olustursun
    Then Rezervasyon basarili sekilde olusturulsun
    And Rezervasyon id ile basarili bir sekilde goruntulensin
    Then Goruntulenen rezervasyon bilgileri kontrol edilsin