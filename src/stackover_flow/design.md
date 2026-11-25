# Design Stackover flow

## Requirements

- User can post questions, answer questions and make comment on questions and answers.
- User can upvote questions, answers and comments.
- User can only downvote questions and answers.
- Questions can have hash tags.
- User can search questions based on tags, keywords and user profiles.
- Asker can mark a answer as the one which helped him with the question as accepted answer.
- The system should assign reputation score to users based on their activity and the quality of their contributions.
- The system should handle concurrent access and ensure data consistency.

### Reputation Score rules

- Base : 0

Others actions on user:

- up vote on question : +3
- down vote on question : -1
- up vote on anwers : +3
- down vote on answer : -1
- up vote on comment : +1
- down vote on comment : -1
- Accept Answer

## Entities & Relationships

Entities:

- User
- Question
- Vote
- Answer
- Comment
- StackOverFlow

Relationships:
One to One, One to many, many to many

- Question has
  - Answer[] (\*)
  - Comment[] (\*)
  - Author(User) (\*)
  - AcceptedAnswer(Answer)
  - Vote[] (\*)
- Anwswer has
  - Comment[] (\*)
  - Vote[] (\*)
  - Author(User)
- Commet has
  - Author(User)
  - Comment[] (\*)
  - Vote[] (\*)
- StackOverFlow has
  - User[] (\*)
  - Question[] (\*)

## Class Design

- StackOverFlow
  - users : User[]
  - Questions : Question[]
  * postQuestion(content: String, author: User, String[] tags) : Question
  * postAnswer(content: String, question: Question, author: User) :Question
  * postComment(content: String, commentable: Commentable)
  * vote(type: VoteType, votable: Votable, String userId)
  * acceptAnswer(String answerId)
  * searchUserQuestions(User user) : Question[]
  * searchQuestions(String keywords) : Question[]

- Commentable:
  - comment(content: String, commenter: User)
  - getComments()

- Votable:
  - vote(type: VoteType, voter: User)
  - countVotes(type: VoteType)

- User
  - name
  - Email(unique)

- Question(Commentable, Votable)
  - content: String
  - author: User
  - hashtags: String[]
  - comments: Comment[]
  - acceptedAnswer: Answer
  - votes : Votes[]
  - createAt: DataTime
  - updatedAt: DateTime
  * addAnswer(Answer answer)
  * acceptAnswer(Answer answer)
  * addComment(Comment commment)
  * getComments()
  * addVote(VoteType type, User voter)
  * getVotes(VoteType type)
  * addTags(String[] tags)

- Vote
  - user: User
  - type: VoteType

- VoteType: Enum
  - UP
  - DOWN

- Answer(Commentable, Votable)
  - question: Question
  - content: String
  - author: User
  - comments: Commet[]
  - votes: Votes[]
  - createdAt: DateTime
  - updatedAt: DateTime
  * addComment(Comment commment)
  * getComments()
  * addVote(VoteType type, User voter)
  * getVotes(VoteType type)

- Comment
  - parent: Commentable
  - content: String
  - user: User
  - votes: Vote[]
  - createdAt: DateTime
  - updatedAt: DateTime
  * addVote(VoteType type, User voter)
  * getVotes(VoteType type)
