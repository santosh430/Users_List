package com.example.userslistmvvm.data.dbhelper

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY name ASC ")
    fun readData():List<User?>

    @Query("UPDATE user_table SET fav=:choice WHERE email = :email")
    suspend fun updateFavUser(email:String,choice:Int)

    @Delete
    suspend fun deleteUser(user: User)
}