package com.arcraiders.companion.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class Skill(
    val id: String,
    val name: String,
    val description: String,
    val tier: Int,
    val cost: Int,
    val isUnlocked: Boolean = false,
    val prerequisites: List<String> = emptyList()
)

data class SkillTreeUiState(
    val skills: List<Skill> = emptyList(),
    val totalPoints: Int = 0,
    val availablePoints: Int = 10,
    val isLoading: Boolean = false
)

@HiltViewModel
class SkillTreeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(SkillTreeUiState())
    val uiState: StateFlow<SkillTreeUiState> = _uiState.asStateFlow()

    init {
        loadSkills()
    }

    private fun loadSkills() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            // Sample skill tree data
            val skills = listOf(
                Skill("combat1", "Combat Mastery I", "Increase weapon damage by 10%", 1, 1),
                Skill("combat2", "Combat Mastery II", "Increase weapon damage by 20%", 2, 2, prerequisites = listOf("combat1")),
                Skill("survival1", "Survival Expert I", "Increase health by 15%", 1, 1),
                Skill("survival2", "Survival Expert II", "Increase health by 30%", 2, 2, prerequisites = listOf("survival1")),
                Skill("tech1", "Tech Specialist I", "Reduce ability cooldown by 10%", 1, 1),
                Skill("tech2", "Tech Specialist II", "Reduce ability cooldown by 20%", 2, 2, prerequisites = listOf("tech1")),
                Skill("ultimate1", "Ultimate Power", "Unlock special ability", 3, 5, prerequisites = listOf("combat2", "survival2", "tech2"))
            )
            
            _uiState.value = _uiState.value.copy(
                skills = skills,
                isLoading = false
            )
        }
    }

    fun toggleSkill(skillId: String) {
        val skills = _uiState.value.skills
        val skill = skills.find { it.id == skillId } ?: return
        
        if (skill.isUnlocked) {
            // Unlock skill - check prerequisites and points
            return
        }
        
        // Check prerequisites
        val prereqsMet = skill.prerequisites.all { prereqId ->
            skills.find { it.id == prereqId }?.isUnlocked == true
        }
        
        if (!prereqsMet) return
        
        // Check available points
        if (_uiState.value.availablePoints < skill.cost) return
        
        val updatedSkills = skills.map {
            if (it.id == skillId) it.copy(isUnlocked = true) else it
        }
        
        _uiState.value = _uiState.value.copy(
            skills = updatedSkills,
            availablePoints = _uiState.value.availablePoints - skill.cost,
            totalPoints = _uiState.value.totalPoints + skill.cost
        )
    }

    fun resetSkills() {
        val resetSkills = _uiState.value.skills.map { it.copy(isUnlocked = false) }
        _uiState.value = _uiState.value.copy(
            skills = resetSkills,
            totalPoints = 0,
            availablePoints = 10
        )
    }
}
