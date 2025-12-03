package com.arcraiders.companion.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arcraiders.companion.ui.viewmodel.Skill
import com.arcraiders.companion.ui.viewmodel.SkillTreeViewModel

@Composable
fun SkillTreeScreen(
    viewModel: SkillTreeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Skill Tree Planner",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Points: ${uiState.availablePoints} available")
            Text("Total spent: ${uiState.totalPoints}")
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(uiState.skills) { skill ->
                    SkillCard(
                        skill = skill,
                        onToggle = { viewModel.toggleSkill(skill.id) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Button(
                onClick = { viewModel.resetSkills() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Reset All Skills")
            }
        }
    }
}

@Composable
fun SkillCard(
    skill: Skill,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (skill.isUnlocked)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = skill.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = skill.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Tier ${skill.tier} • ${skill.cost} point(s)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (skill.prerequisites.isNotEmpty()) {
                    Text(
                        text = "Prerequisites: ${skill.prerequisites.size} skill(s)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            Button(
                onClick = onToggle,
                enabled = !skill.isUnlocked
            ) {
                Text(if (skill.isUnlocked) "Unlocked" else "Unlock")
            }
        }
    }
}
