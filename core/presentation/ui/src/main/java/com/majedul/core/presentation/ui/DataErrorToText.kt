import com.majedul.core.domain.util.DataError
import com.majedul.core.presentation.ui.R
import com.majedul.core.presentation.ui.UiText

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Local.DISK_FULL -> {
            UiText.StringResource(R.string.error_disk_full)
        }

        DataError.Network.REQUEST_TIMEOUT -> {
            UiText.StringResource(R.string.error_request_timeout)
        }

        DataError.Network.UNAUTHORIZED -> {
            UiText.StringResource(R.string.error_unauthorized)
        }

        DataError.Network.NO_INTERNET -> {
            UiText.StringResource(R.string.error_no_internet)
        }

        DataError.Network.SERVER_ERROR -> {
            UiText.StringResource(R.string.error_server_error)
        }

        DataError.Network.SERIALIZATION -> {
            UiText.StringResource(R.string.error_serialization)
        }

        else -> {
            UiText.StringResource(R.string.error_unknown)

        }
    }
}