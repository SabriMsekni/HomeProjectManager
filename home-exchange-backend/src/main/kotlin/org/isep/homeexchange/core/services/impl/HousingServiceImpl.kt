package org.isep.homeexchange.core.services.impl

import org.isep.homeexchange.core.dto.CreateHousingDto
import org.isep.homeexchange.core.dto.HousingDto
import org.isep.homeexchange.core.dto.toDao
import org.isep.homeexchange.core.services.HousingService
import org.isep.homeexchange.core.services.UserService
import org.isep.homeexchange.infrastructure.dao.HousingDao
import org.isep.homeexchange.infrastructure.dao.toHousingDto
import org.isep.homeexchange.infrastructure.repository.HousingRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class HousingServiceImpl(
    private val housingRepository: HousingRepository,
    private val userService: UserService,
) : HousingService {

    override fun create(dto: CreateHousingDto): HousingDto {
        val userDto = userService.getById(dto.userId)
        val housingDao = dto.toDao()
        housingDao.user = userDto.toDao()

        return housingRepository.save(housingDao).toHousingDto()
    }

    override fun getById(id: Long): HousingDto {
        val housing: Optional<HousingDao> = housingRepository.findById(id);

        if (housing.isEmpty) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Housing doesn't exist")
        }

        return housing.get().toHousingDto()
    }

    override fun getByUsers(userId: Long): List<HousingDto> {
        val housingsDao: List<HousingDao> = housingRepository.findByUserId(userId)

        return housingsDao.toHousingDto()
    }

    override fun getAll(): List<HousingDto> {
        val housingsDao: List<HousingDao> = housingRepository.findAll();

        return housingsDao.toHousingDto()
    }

    override fun updateHousing(dto: HousingDto): HousingDto {
        val userDto = userService.getById(dto.userId)
        val housingDao = dto.toDao()
        housingDao.user = userDto.toDao()

        return housingRepository.save(housingDao).toUserDto()
    }

    override fun deleteById(id: Long) {
        val housing: Optional<HousingDao> = housingRepository.findById(id)

        if(housing.isEmpty) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist")
        }

        housingRepository.deleteById(id)
    }

    override fun deleteAll() {
        housingRepository.deleteAll()
    }
}