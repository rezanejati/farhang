package nejati.me.omdbapi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import nejati.me.omdbapi.api.RxSingleSchedulers
import nejati.me.omdbapi.service.model.request.OmdpiRequestModel
import nejati.me.omdbapi.view.fragment.movie.SearchFragmentNavigator
import nejati.me.omdbapi.webServices.farhangModel.search.response.search.OmdbpiSearchrResponse
import nejati.me.sample.di.api.FarhangApi
import org.junit.Assert
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


/**
 * Authors:
 * Reza Nejati <rn.nejati@gmail.com>
 * Copyright © 2020
 */
class ViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    internal var apiClient: FarhangApi? = null
    @Mock
    private var viewModel: MovieViewModel? = null

    @Mock
    internal var result: OmdbpiSearchrResponse?=null

    @Mock
    internal var lifecycleOwner: LifecycleOwner? = null

    @Mock
    internal var lifecycle: Lifecycle? = null

    @Mock
    internal var requestModel: OmdpiRequestModel? = null

    @Mock
    internal var searchFragmentNavigator: SearchFragmentNavigator? = null


    @Before
    @Throws(Exception::class)
    fun setUp() {

        MockitoAnnotations.initMocks(this)
         lifecycle = LifecycleRegistry(lifecycleOwner!!)
        viewModel =
            MovieViewModel(
                apiClient!!,
                RxSingleSchedulers.TEST_SCHEDULER
            )
        viewModel!!.moviesResult.addAll(result!!.search!!)

        viewModel!!.navigator=searchFragmentNavigator

        viewModel!!.showErrorLayout.set(false)
        viewModel!!.showProgressLayout.set(false)
        viewModel!!.showWaitingSearchLayout.set(true)
        viewModel!!.showPaginationProgress.set(false)
        viewModel!!.showResultRecyclerView.set(false)
    }

    @Test
    fun moviesResult() {
        Mockito.`when`(apiClient!!.getMovies(requestModel!!)).thenReturn(null)
        assertNotNull(viewModel!!.moviesResult)
        assertTrue(viewModel!!.moviesResult.isEmpty())
    }

    @Test
    fun onError() {
        viewModel!!.onErrorRetroClient()
        Assert.assertEquals(true, viewModel!!.showErrorLayout.get());
    }
    @Test
    fun onStartWebservice(){
        viewModel!!.onStartWebservice()
        Assert.assertEquals(true, viewModel!!.showProgressLayout.get());
        Assert.assertEquals(false, viewModel!!.showWaitingSearchLayout.get());
        Assert.assertEquals(false, viewModel!!.showErrorLayout.get());
        Assert.assertEquals(false, viewModel!!.showPaginationProgress.get());

    }
    @Before
    fun beforFinishWebService() {
        viewModel!!.onStartWebservice()
    }

    @Test
    fun onFinishWebService(){
        viewModel!!.onFinishWebService()
        Assert.assertEquals(false, viewModel!!.showProgressLayout.get());
        Assert.assertEquals(true, viewModel!!.showResultRecyclerView.get());
        Assert.assertEquals(false, viewModel!!.showPaginationProgress.get());
        Assert.assertEquals(false, viewModel!!.showWaitingSearchLayout.get());
        Assert.assertEquals(false, viewModel!!.showErrorLayout.get());
    }

}
