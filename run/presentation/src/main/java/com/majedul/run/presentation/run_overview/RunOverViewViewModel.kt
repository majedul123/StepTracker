package com.majedul.run.presentation.run_overview

import androidx.lifecycle.ViewModel

class RunOverViewViewModel: ViewModel() {


    fun onAction(action: RunOverviewAction){
        when(action){
            RunOverviewAction.OnAnalyticsClick -> {

            }
            RunOverviewAction.OnLogoutCLick -> {

            }
            RunOverviewAction.OnStartClick -> {

            }
        }

    }
}