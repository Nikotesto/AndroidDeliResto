package fr.isen.Nicolas_LEBON.network

import java.io.Serializable

class RegisterResult(val data: User) {}

class User(val id: Int): Serializable {}