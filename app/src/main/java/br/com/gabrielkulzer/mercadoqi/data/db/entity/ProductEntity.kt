package br.com.gabrielkulzer.mercadoqi.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "product")
//Classe representando os produtos
data class ProductEntity (
    //Ao adicionar um novo produto na tabela sera gerado um id automaticamente
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val price: Float,
    val quantity: Int
    ): Parcelable
