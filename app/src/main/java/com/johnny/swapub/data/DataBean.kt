package com.johnny.swapub.data

import com.johnny.swapub.R

class DataBean {
    var imageRes: Int? = null
    var imageUrl: String? = null
    var title: String?
    var viewType: Int

    constructor(imageRes: Int?, title: String?, viewType: Int) {
        this.imageRes = imageRes
        this.title = title
        this.viewType = viewType
    }

    constructor(imageUrl: String?, title: String?, viewType: Int) {
        this.imageUrl = imageUrl
        this.title = title
        this.viewType = viewType
    }

    companion object {

        //test image (change others)
        val testData3: List<DataBean>
            get() {
                val list: MutableList<DataBean> = ArrayList()
                list.add(
                        DataBean("https://s.yimg.com/ny/api/res/1.2/sZ_.ASWb7dsobu8YUGai5w--/YXBwaWQ9aGlnaGxhbmRlcjt3PTk2MDtoPTYwMC4xMjM0MTQ0NjY5MTgx/https://s.yimg.com/os/creatr-uploaded-images/2020-12/afed7640-4411-11eb-9ffc-e9c36c659e2a",
                                null,
                                1
                        )
//"https://img.zcool.cn/community/013de756fb63036ac7257948747896.jpg"
                )
                list.add(
                        DataBean("https://images.pexels.com/photos/5821032/pexels-photo-5821032.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
                                ,
                                null,
                                1
                        )
//"https://img.zcool.cn/community/01639a56fb62ff6ac725794891960d.jpg"
                )
                list.add(
                        DataBean("https://images.pexels.com/photos/2844982/pexels-photo-2844982.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
                                ,
                                null,
                                1
                        )
//"https://img.zcool.cn/community/01270156fb62fd6ac72579485aa893.jpg"
                )
                list.add(
                        DataBean("https://images.pexels.com/photos/1050256/pexels-photo-1050256.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
                                ,
                                null,
                                1
                        )
//"https://img.zcool.cn/community/01233056fb62fe32f875a9447400e1.jpg"
                )
                list.add(
                        DataBean("https://images.pexels.com/photos/3736756/pexels-photo-3736756.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
                                ,
                                null,
                                1
                        )
//"https://img.zcool.cn/community/016a2256fb63006ac7257948f83349.jpg"
                )
                return list
            }
    }
}
