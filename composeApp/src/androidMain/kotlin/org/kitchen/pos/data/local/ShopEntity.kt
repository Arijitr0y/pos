package org.kitchen.pos.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.kitchen.pos.data.shop.Shop

@Entity(tableName = "shop")
data class ShopEntity(
    @PrimaryKey val id: Long = 1L,          // single row
    val name: String,
    val ownerName: String,
    val phone: String,
    val email: String,
    val addressLine1: String,
    val addressLine2: String,
    val city: String,
    val state: String,
    val pincode: String,
    val gstin: String?,
    val currencyCode: String,
    val taxInclusivePricing: Boolean,
    val allowNegativeStock: Boolean,
    val createdAtMillis: Long,
    val updatedAtMillis: Long,
)

fun ShopEntity.toDomain(): Shop =
    Shop(
        id = id,
        name = name,
        ownerName = ownerName,
        phone = phone,
        email = email,
        addressLine1 = addressLine1,
        addressLine2 = addressLine2,
        city = city,
        state = state,
        pincode = pincode,
        gstin = gstin,
        currencyCode = currencyCode,
        taxInclusivePricing = taxInclusivePricing,
        allowNegativeStock = allowNegativeStock,
        createdAtMillis = createdAtMillis,
        updatedAtMillis = updatedAtMillis
    )

fun Shop.toEntity(): ShopEntity =
    ShopEntity(
        id = id,
        name = name,
        ownerName = ownerName,
        phone = phone,
        email = email,
        addressLine1 = addressLine1,
        addressLine2 = addressLine2,
        city = city,
        state = state,
        pincode = pincode,
        gstin = gstin,
        currencyCode = currencyCode,
        taxInclusivePricing = taxInclusivePricing,
        allowNegativeStock = allowNegativeStock,
        createdAtMillis = createdAtMillis,
        updatedAtMillis = updatedAtMillis
    )
