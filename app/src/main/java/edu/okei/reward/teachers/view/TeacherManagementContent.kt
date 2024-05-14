package edu.okei.reward.teachers.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import edu.okei.core.domain.model.teacher.TeacherModel
import edu.okei.core.domain.model.user.shortenFullName
import edu.okei.reward.R
import edu.okei.reward.common.ui.click.alphaClick
import edu.okei.reward.common.ui.edit_text.OutlinedEditText
import edu.okei.reward.common.ui.theme.dimensions
import edu.okei.reward.common.ui.user.getStringRole
import edu.okei.reward.common.view_model.EventBase
import edu.okei.reward.teachers.model.TeachersEvent
import edu.okei.reward.teachers.model.TeachersState

@Composable
fun TeacherManagementContent(
    state: TeachersState.TeacherManagement,
    eventBase: EventBase<TeachersEvent>,
    listState: LazyListState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        OutlinedEditText(
            value = state.searchText,
            onValueChange = {
                eventBase.onEvent(TeachersEvent.InputSearch(it))
            },
            hint = stringResource(R.string.search),
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.dimensions.grid_4
                ),
            textStyle = MaterialTheme.typography.body2
                .copy(
                    color = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Center,
                )
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f),
            contentPadding = PaddingValues(
                top = MaterialTheme.dimensions.grid_5_5,
                start = MaterialTheme.dimensions.grid_4,
                end = MaterialTheme.dimensions.grid_4,
                bottom = (WindowInsets.navigationBars
                    .asPaddingValues().calculateBottomPadding() +
                        MaterialTheme.dimensions.grid_5_5 * 4f)
            ),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.grid_3_5),
            state = listState,
        ){
            items(state.listTeacher){model ->
               TeacherItem(model = model){ id->
                   eventBase.onEvent(TeachersEvent.DeleteUser(id))
               }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TeacherItem(
    model: TeacherModel,
    onDelete: (id: String)-> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                MaterialTheme.dimensions.borderSmall,
                MaterialTheme.colors.primary,
                MaterialTheme.shapes.medium
            )
            .padding(
                vertical = MaterialTheme.dimensions.grid_4_5,
                horizontal = MaterialTheme.dimensions.grid_5_5 * 1.5f
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement
                .spacedBy(MaterialTheme.dimensions.grid_2)
        ) {
            Text(
                text = shortenFullName(model.fullname),
                style = MaterialTheme.typography.h5
                    .copy(
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.W300
                    ),
                maxLines = 1, overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .basicMarquee()
            )
            Text(
                text = "${stringResource(R.string.user_role)}: ${getStringRole(model.role)}",
                style = MaterialTheme.typography.caption
                    .copy(color = MaterialTheme.colors.primary),
                maxLines = 1
            )
        }
        Icon(
            painter = painterResource(R.drawable.ic_delete) ,
            contentDescription = null,
            tint = MaterialTheme.colors.primary,
            modifier = Modifier
                .alphaClick { onDelete(model.login) }
                .scale(MaterialTheme.dimensions.coefficient)
                .size(28.dp)
        )
    }

}