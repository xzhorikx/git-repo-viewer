package alex.zhurkov.git_repo_viewer.feature.main.presentation

import alex.zhurkov.git_repo_viewer.domain.model.GitHubReposPage
import alex.zhurkov.git_repo_viewer.domain.model.RepoFilter
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito


class MainActivityReducerTest {
    private val reducer = MainActivityReducer()
    private val initialState = MainActivityState.EMPTY

    @Test
    fun `PageLoaded test`() {
        val value = Mockito.mock(GitHubReposPage::class.java)
        val change = MainActivityChange.PageLoaded(value)
        val expectedState = initialState.copy(pages = listOf(value))
        val newState = reducer.reduce(initialState, change)
        assertEquals(expectedState, newState)
    }

    @Test
    fun `PageLoadingChanged test`() {
        val value = true
        val change = MainActivityChange.PageLoadingChanged(isLoading = value)
        val expectedState = initialState.copy(isPageLoading = value)
        val newState = reducer.reduce(initialState, change)
        assertEquals(expectedState, newState)
    }

    @Test
    fun `RefreshChanged test`() {
        val value = true
        val change = MainActivityChange.RefreshChanged(isRefreshing = value)
        val expectedState = initialState.copy(isRefreshing = value)
        val newState = reducer.reduce(initialState, change)
        assertEquals(expectedState, newState)
    }

    @Test
    fun `FilterChanged test`() {
        val value = RepoFilter.Favorites
        val change = MainActivityChange.FilterChanged(value)
        val expectedState = initialState.copy(repoFilter = value)
        val newState = reducer.reduce(initialState, change)
        assertEquals(expectedState, newState)
    }

    @Test
    fun `ItemsCleared test`() {
        val currentState =
            MainActivityState.EMPTY.copy(pages = listOf(Mockito.mock(GitHubReposPage::class.java)))
        val change = MainActivityChange.ItemsCleared
        val expectedState = MainActivityState.EMPTY
        val newState = reducer.reduce(currentState, change)
        assertEquals(expectedState, newState)
    }

    @Test
    fun `LastVisibleItemChanged test`() {
        val value = 123L
        val change = MainActivityChange.LastVisibleItemChanged(id = value)
        val expectedState = initialState.copy(lastVisibleItemId = value)
        val newState = reducer.reduce(initialState, change)
        assertEquals(expectedState, newState)
    }

    @Test
    fun `FavoritesChanged test`() {
        val value = listOf(123L, 5555555L)
        val change = MainActivityChange.FavoritesChanged(data = value)
        val expectedState = initialState.copy(favorites = value)
        val newState = reducer.reduce(initialState, change)
        assertEquals(expectedState, newState)
    }

    @Test
    fun `NetworkChanged test`() {
        val value = true
        val change = MainActivityChange.NetworkChanged(isConnected = value)
        val expectedState = initialState.copy(isNetworkConnected = value)
        val newState = reducer.reduce(initialState, change)
        assertEquals(expectedState, newState)
    }
}