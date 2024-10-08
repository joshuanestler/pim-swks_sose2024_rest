﻿using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Rest.AspNet.Data;
using Rest.AspNet.Models;

namespace Rest.AspNet.Controllers;

[ApiController]
[Route("posts/{postId}/comments")]
public class CommentController(BlogContext context) : ControllerBase {
    
    [AllowAnonymous]
    [HttpGet]
    public async Task<ActionResult<IEnumerable<Comment>>> GetComments(ulong postId) {
        return await GetPostComments(postId);
    }
    
    [HttpPost]
    public async Task<ActionResult<Comment>> CreateComment(ulong postId, [FromBody] Comment comment) {
        Post? existingPost = await context.Posts.FindAsync(postId);
        if (existingPost == null) {
            return NotFound();
        }

        comment.PostId = postId;
        comment.Post = existingPost;
        
        context.Comments.Add(comment);
        await context.SaveChangesAsync();
        return CreatedAtAction(nameof(CreateComment), new { id = comment.Id }, comment);
    }
    
    [AllowAnonymous]
    [HttpGet("{commentId}")]
    public async Task<ActionResult<Comment>> GetCommentById(ulong postId, ulong commentId) {
        
        Post? existingPost = await context.Posts.FindAsync(postId);
        if (existingPost == null) {
            return NotFound();
        }
        
        Comment? existingComment = (await GetPostComments(postId)).FirstOrDefault(c => c.Id == commentId);
        if (existingComment == null) {
            return NotFound();
        }
        
        return existingComment;
    }
    
    [HttpPut("{commentId}")]
    public async Task<ActionResult<Comment>> UpdateComment(ulong postId, ulong commentId, [FromBody] Comment comment) {

        Comment? existingComment = (await GetPostComments(postId)).FirstOrDefault(c => c.Id == commentId);
        if (existingComment == null) {
            return NotFound();
        }
        
        existingComment.Content = comment.Content;
        
        await context.SaveChangesAsync();
        return existingComment;
    }
    
    [HttpDelete("{commentId}")]
    public async Task<ActionResult<Comment>> DeleteComment(ulong postId, ulong commentId) {
        
        Comment? existingComment = (await GetPostComments(postId)).FirstOrDefault(c => c.Id == commentId);
        if (existingComment == null) {
            return NotFound();
        }
        
        context.Comments.Remove(existingComment);
        await context.SaveChangesAsync();
        
        return existingComment;
    }

    private async Task<List<Comment>> GetPostComments(ulong postId)
    {
        return await context.Comments.Where(c => c.PostId == postId).ToListAsync();
    }
}
