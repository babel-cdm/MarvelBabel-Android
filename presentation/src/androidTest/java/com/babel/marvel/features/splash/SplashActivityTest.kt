package com.babel.marvel.features.splash

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SplashActivityTest {
    /**
    private var repository: MarvelRepository = mock()
    lateinit var response: GetAllCharacterUseCase

    @Before
    fun setUp(){
        response = GetAllCharacterUseCase(repository)
    }

    @Rule
    @JvmField
    var mActivityTestRule = object : ActivityTestRule<SplashActivity>(SplashActivity::class.java){
        override fun getActivityIntent(): Intent {
            return Intent(InstrumentationRegistry.getTargetContext(), MainActivity::class.java)
                .apply {
                    putExtra("TEST", "TEST")
                }
        }
    }

    @Test
    fun activityFlowTest() {
        val recyclerView = onView(
            allOf(
                withId(R.id.rvCompleteList),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(19, click()))

        val recyclerView2 = onView(
            allOf(
                withId(R.id.rvCompleteList),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(1, click()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
    **/
}
