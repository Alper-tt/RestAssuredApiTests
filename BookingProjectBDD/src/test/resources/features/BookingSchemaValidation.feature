Feature: Rezervasyon semasi istenilen duzende olsun
  Scenario: Goruntulenen rezervasyon semasi istenilen duzende olsun
    Given Kullanici rezervasyon icin giris bilgilerini girsin
    When Kullanici rezervasyonu olustursun
    Then Rezervasyon basarili sekilde olusturulsun
    And Rezervasyon id ile basarili bir sekilde goruntulensin
    Then Goruntulenen rezervasyon bilgileri kontrol edilsin
    And Rezervasyon semasi istenilen duzende olsun