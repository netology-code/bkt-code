package ru.netology.service

import io.ktor.features.NotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import ru.netology.dto.*
import ru.netology.exception.InvalidPasswordException
import ru.netology.exception.UsernameAlreadyRegisteredException
import ru.netology.model.UserModel
import ru.netology.repository.UserRepository

class UserService(
    private val repo: UserRepository,
    private val tokenService: JWTTokenService,
    private val passwordEncoder: PasswordEncoder
) {
    suspend fun getModelById(id: Long): UserModel? {
        return repo.getById(id)
    }

    suspend fun getById(id: Long): UserResponseDto {
        val model = repo.getById(id) ?: throw NotFoundException()
        return UserResponseDto.fromModel(model)
    }

    suspend fun getByIds(ids: Collection<Long>): List<UserResponseDto> {
        return repo.getByIds(ids).map { UserResponseDto.fromModel(it) }
    }

    suspend fun authenticate(input: AuthenticationRequestDto): AuthenticationResponseDto {
        val model = repo.getByUsername(input.username) ?: throw NotFoundException()
        if (!passwordEncoder.matches(input.password, model.password)) {
            throw InvalidPasswordException("Wrong password!")
        }

        val token = tokenService.generate(model.id)
        return AuthenticationResponseDto(token)
    }

    suspend fun register(input: RegistrationRequestDto): RegistrationResponseDto {
        val model = repo.saveByNameIfNotExists(
            UserModel(username = input.username, password = passwordEncoder.encode(input.password))
        ) ?: throw UsernameAlreadyRegisteredException(input.username)

        val token = tokenService.generate(model.id)
        return RegistrationResponseDto(token)
    }
}