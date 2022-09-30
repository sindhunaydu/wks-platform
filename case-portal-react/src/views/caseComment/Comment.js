import { Avatar, Box, Typography } from '@mui/material';
import Button from '@mui/material/Button';
import CommentForm from './CommentForm';

import User1 from 'assets/images/users/avatar-3.png';
import MainCard from 'components/MainCard';

const Comment = ({
    comment,
    replies,
    setActiveComment,
    activeComment,
    updateComment,
    deleteComment,
    addComment,
    parentId = null,
    currentUserId
}) => {
    const isEditing = activeComment && activeComment.id === comment.id && activeComment.type === 'editing';
    const isReplying = activeComment && activeComment.id === comment.id && activeComment.type === 'replying';
    const fiveMinutes = 300000;
    const timePassed = new Date() - new Date(comment.createdAt) > fiveMinutes;
    const canDelete = currentUserId === comment.userId && replies.length === 0 && !timePassed;
    const canReply = Boolean(currentUserId);
    const canEdit = currentUserId === comment.userId && !timePassed;
    const replyId = parentId ? parentId : comment.id;
    const createdAt = new Date(comment.createdAt).toLocaleDateString();
    return (
        <MainCard sx={{ mb: 1 }}>
            <div key={comment.id} className="comment">
                <div className="comment-image-container">
                    <Avatar alt="John Doe" src={User1} />
                </div>
                <div className="comment-right-part">
                    <div className="comment-content">
                        <Box sx={{ display: 'flex', flexDirection: 'row', m: 1 }}>
                            <Typography sx={{ p: 0.5 }} variant="h5">
                                {comment.username}
                            </Typography>
                            <Typography sx={{ p: 0.5 }}>{createdAt}</Typography>
                        </Box>
                    </div>
                    {!isEditing && <Typography variant="h6">{comment.body}</Typography>}
                    {isEditing && (
                        <CommentForm
                            submitLabel="Update"
                            hasCancelButton
                            initialText={comment.body}
                            handleSubmit={(text) => updateComment(text, comment.id)}
                            handleCancel={() => {
                                setActiveComment(null);
                            }}
                        />
                    )}
                    <div className="comment-actions">
                        {canReply && (
                            <Button className="comment-action" onClick={() => setActiveComment({ id: comment.id, type: 'replying' })}>
                                Reply
                            </Button>
                        )}
                        {canEdit && (
                            <Button className="comment-action" onClick={() => setActiveComment({ id: comment.id, type: 'editing' })}>
                                Edit
                            </Button>
                        )}
                        {canDelete && (
                            <Button className="comment-action" onClick={() => deleteComment(comment.id)}>
                                Delete
                            </Button>
                        )}
                    </div>
                    {isReplying && <CommentForm submitLabel="Reply" handleSubmit={(text) => addComment(text, replyId)} />}
                    {replies.length > 0 && (
                        <div className="replies">
                            {replies.map((reply) => (
                                <Comment
                                    comment={reply}
                                    key={reply.id}
                                    setActiveComment={setActiveComment}
                                    activeComment={activeComment}
                                    updateComment={updateComment}
                                    deleteComment={deleteComment}
                                    addComment={addComment}
                                    parentId={comment.id}
                                    replies={[]}
                                    currentUserId={currentUserId}
                                />
                            ))}
                        </div>
                    )}
                </div>
            </div>
        </MainCard>
    );
};

export default Comment;