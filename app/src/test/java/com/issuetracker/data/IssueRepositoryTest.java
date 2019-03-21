package com.issuetracker.data;

import com.issuetracker.common.IssueStates;
import com.issuetracker.data.dao.ChangelogDAO;
import com.issuetracker.data.dao.CommentDAO;
import com.issuetracker.data.dao.IssueDAO;
import com.issuetracker.data.entities.IssueEntity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class IssueRepositoryTest {

    private IssueRepository sut;
    @Mock
    private IssueDAO issueDAO;
    @Mock
    private CommentDAO commentDAO;
    @Mock
    private ChangelogDAO changelogDAO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sut = new IssueRepository(issueDAO, commentDAO, changelogDAO);
    }

    @Test
    public void create() {
        // Arrange
        String title = "title";

        ArgumentCaptor<IssueEntity> captor = ArgumentCaptor.forClass(IssueEntity.class);

        // Act
        Long actualId = sut.create(title, null);

        // Assert
        verify(issueDAO, times(1)).save(captor.capture());
        assertNull(captor.getValue().getId());
        assertThat(captor.getValue().getTitle(), is(title));
        assertThat(captor.getValue().getState(), is(IssueStates.TO_DO));
        assertNotNull(actualId);
    }

    @Test
    public void remove() {
        // Arrange
        long issueId = 9127;
        sut = spy(sut);

        // Act
        sut.remove(issueId);

        // Assert
        verify(sut, times(1)).remove(issueId);
    }

    @Test
    public void updateState_whenIssueEntityExists() {
        // Arrange
        long issueId = 9127;
        String title = "title";
        long userId = 9248;
        long date = 12345;
        IssueEntity issueEntity = new IssueEntity(title, IssueStates.TO_DO, date, userId);
        issueEntity.setId(issueId);
        int newState = 2;

        when(issueDAO.getById(issueId)).thenReturn(issueEntity);

        // Act
        sut.updateState(issueId, newState);

        // Assert
        assertThat(issueEntity.getState(), is(newState));
        verify(issueDAO, times(1)).save(issueEntity);
    }

    @Test
    public void updateState_whenIssueEntityDoesNotExist() {
        // Arrange
        long issueId = 9127;
        IssueEntity issueEntity = null;
        int newState = 2;

        when(issueDAO.getById(issueId)).thenReturn(issueEntity);

        // Act
        sut.updateState(issueId, newState);

        // Assert
        verify(issueDAO, times(0)).save(any(IssueEntity.class));
    }

    @Test
    public void updateUser_whenIssueEntityExists() {
        // Arrange
        long issueId = 9127;
        String title = "title";
        long userId = 9248;
        long date = 12345;
        IssueEntity issueEntity = new IssueEntity(title, IssueStates.TO_DO, date, userId);
        issueEntity.setId(issueId);
        long newUser = 2;

        when(issueDAO.getById(issueId)).thenReturn(issueEntity);

        // Act
        sut.updateUser(issueId, newUser);

        // Assert
        assertThat(issueEntity.getUserId(), is(newUser));
        verify(issueDAO, times(1)).save(issueEntity);
    }

    @Test
    public void updateUser_whenIssueEntityDoesNotExist() {
        // Arrange
        long issueId = 9127;
        IssueEntity issueEntity = null;
        int newState = 2;

        when(issueDAO.getById(issueId)).thenReturn(issueEntity);

        // Act
        sut.updateUser(issueId, newState);

        // Assert
        verify(issueDAO, times(0)).save(any(IssueEntity.class));
    }

    @Test
    public void getIssue_whenIssueEntityExists() {
        // Arrange
        long issueId = 9127;
        String title = "title";
        long userId = 9248;
        long date = 12345;
        IssueEntity issueEntity = new IssueEntity(title, IssueStates.TO_DO, date, userId);
        issueEntity.setId(issueId);
        long newUser = 2;

        when(issueDAO.getById(issueId)).thenReturn(issueEntity);

        // Act
        sut.updateUser(issueId, newUser);

        // Assert
        assertThat(issueEntity.getUserId(), is(newUser));
        verify(issueDAO, times(1)).save(issueEntity);
    }
}
