package android.example.biologytest.hilt

import android.content.Context
import android.example.biologytest.MyFragmentFactory
import android.example.biologytest.model.*
import android.example.biologytest.model.daos.*
import android.example.biologytest.repository.*
import androidx.fragment.app.FragmentFactory
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(ApplicationComponent::class)
object MyModule {

    lateinit var database: MyDatabase

    @Singleton
    @Provides
    fun provideFragmentFactory(): FragmentFactory {
        return MyFragmentFactory()
    }

    @Singleton
    @Provides
    fun provideMyDatabase(@ApplicationContext context: Context): MyDatabase {
        database = Room
            .databaseBuilder(
                context,
                MyDatabase::class.java,
                MyDatabase.DATABASE_NAME
            )
            .addMigrations(MIGRATION_10_11, MIGRATION_11_12, MIGRATION_12_13, MIGRATION_13_14)
            .createFromAsset("database.db")
            /*.addCallback(object : RoomDatabase.Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    GlobalScope.launch {
                        database.questionDao().insert(QuestionEntity(0,QuestionTypeEnum.SPECIFIC_NUMBER,"Kolik má člověk zubů?"))
                        database.questionDao().insert(QuestionEntity(0,QuestionTypeEnum.SPECIFIC_NUMBER,"Kolik má člověk kostí?"))
                        database.questionDao().insert(QuestionEntity(0,QuestionTypeEnum.SINGLE_CORRECT,"U většiny rostlin jsou průduchy vyvinuty hlavně na spodní straně listů."))
                        database.questionDao().insert(QuestionEntity(0,QuestionTypeEnum.MULTIPLE_CORRECT, "Transpirační proud vede:" ))

                        database.definedAnswerDao().insert(DefinedAnswerEntity(0,"32", 1, true))
                        database.definedAnswerDao().insert(DefinedAnswerEntity(0, "207", 2, true))
                        database.definedAnswerDao().insert(DefinedAnswerEntity(0, "Ano", 3, true))
                        database.definedAnswerDao().insert(DefinedAnswerEntity(0, "Ne", 3, false))
                        database.definedAnswerDao().insert(DefinedAnswerEntity(0, "xylémem organické látky z kořene do listů", 4, true))
                        database.definedAnswerDao().insert(DefinedAnswerEntity(0, "floémem produkty fotosyntézy z listů na místa spotřeby", 4, false))
                        database.definedAnswerDao().insert(DefinedAnswerEntity(0, "přivádí xylémem roztoky minerálních látek z půdy", 4, true))
                        database.definedAnswerDao().insert(DefinedAnswerEntity(0, "sítkovicemi proud asimilátů z listů do rostoucích orgánů", 4, false))
                    }
                }
            })*/
            .build()
        return database
    }

    //region DAO
    @Singleton
    @Provides
    fun provideQuestionDao(db: MyDatabase): QuestionDao = db.questionDao()

    @Singleton
    @Provides
    fun provideDefinedAnswerDao(db: MyDatabase): DefinedAnswerDao = db.definedAnswerDao()

    @Singleton
    @Provides
    fun provideExamDao(db: MyDatabase): ExamDao = db.examDao()

    @Singleton
    @Provides
    fun provideAnswerDao(db: MyDatabase): AnswerDao = db.answerDao()

    @Singleton
    @Provides
    fun provideTopicGroupDao(db: MyDatabase): TopicGroupDao = db.topicGroupDao()
    //endregion

    //region Repository
    @Singleton
    @Provides
    fun provideQuestionRepository(questionDao: QuestionDao) =
        QuestionRepository(questionDao)

    @Singleton
    @Provides
    fun provideDefinedAnswerRepository(definedAnswerDao: DefinedAnswerDao) =
        DefinedAnswerRepository(definedAnswerDao)

    @Singleton
    @Provides
    fun provideExamRepository(examDao: ExamDao) =
        ExamRepository(examDao)

    @Singleton
    @Provides
    fun provideAnswerRepository(answerDao: AnswerDao) =
        AnswerRepository(answerDao)

    @Singleton
    @Provides
    fun provideTopicGroupRepository(topicGroupDao: TopicGroupDao) =
        TopicGroupRepository(topicGroupDao)
    //endregion
}