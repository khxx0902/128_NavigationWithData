@file:OptIn (
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class)
package com.example.praktikum6

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.praktikum6.data.SumberData.flavors
import com.example.praktikum6.komponen.OrderViewModel

enum class PengelolaHalaman {
    Home,
    Fomulir,
    Rasa,
    Summary,
}


@Composable
fun EsJumboAppBar(
    bisaNavigasiBack: Boolean,
    navigasiUp: () -> Unit,
    modifier : Modifier = Modifier
){
    TopAppBar(title = {Text(stringResource(id = R.string.app_name))},
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = modifier,
        navigationIcon = {
            if (bisaNavigasiBack){
                IconButton(onClick = navigasiUp){
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun EsJumboApp(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){
    Scaffold (
        topBar = {
            EsJumboAppBar(
                bisaNavigasiBack = false,
                navigasiUp = {/* TODO: implement back navigation*/}
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.stateUI.collectAsState()
        NavHost(
            navController = navController,
            startDestination = PengelolaHalaman.Home.name,
            modifier = Modifier.padding(innerPadding)
        )
        {
            composable(route = PengelolaHalaman.Home.name) {
                HalamanHome(
                    onNextButtonClicked = {
                        navController.navigate(PengelolaHalaman.Fomulir.name)
                    })
            }

            composable(route = PengelolaHalaman.Fomulir.name) {
                HalamanSatu(onSubmitButtonClicked = {
                    viewModel.setContact(it)
                    navController.navigate(PengelolaHalaman.Rasa.name)
                })
            }

            composable(route = PengelolaHalaman.Rasa.name){
                val context = LocalContext.current
                HalamanDua(
                    pilihanRasa = flavors.map{ id -> context.resources.getString(id)},
                    onSelectionChanged = {viewModel.setRasa(it)},
                    onnConfirmButtonClicked = {viewModel.setJumlah(it)},
                    onNextButtonClicked = {navController.navigate(PengelolaHalaman.Summary.name)},
                    onCencelButtonClicked = { /*TODO*/ })
            }

            composable(route = PengelolaHalaman.Summary.name){
                HalamanTiga(
                    OrderUIState = uiState,
                    onCencelButtonClicked = { /*TODO*/ })
            }





        }

    }
}

