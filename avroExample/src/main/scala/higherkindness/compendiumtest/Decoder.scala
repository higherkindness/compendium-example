package higherkindness.compendiumtest

import kantan.csv.RowDecoder

object Decoder {

  implicit val productDecoder: RowDecoder[Product] = RowDecoder.ordered {
    (id: String, desc: String, color: String, size: String) =>
      Product(id, desc, color, size)
  }

  implicit val supplierDecoder: RowDecoder[Supplier] = RowDecoder.ordered {
    (id: String, name: String, email: String, phone: String) =>
      Supplier(id, name, email, phone)
  }

  implicit val materialDecoder: RowDecoder[Material] = RowDecoder.ordered {
    (name: String, code: String, shipId: String) =>
      Material(name, code, shipId)
  }

  implicit val saleDecoder: RowDecoder[Sale] = RowDecoder.ordered {
    (ic: String,
     n: String,
     sn: String,
     em: String,
     ipr: String,
     d: String,
     c: String,
     si: String) =>
      Sale(Client(ic, n, sn, em), Product(ipr, d, c, si))
  }
}
