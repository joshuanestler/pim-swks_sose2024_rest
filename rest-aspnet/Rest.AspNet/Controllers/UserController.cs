﻿using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Rest.AspNet.Data;
using Rest.AspNet.Models;

namespace Rest.AspNet.Controllers;

[ApiController]
[Route("users")]
public class UserController(BlogContext context) : ControllerBase {
    
    [HttpGet]
    public async Task<ActionResult<IEnumerable<User>>> GetUsers() {
        return await context.Users.ToListAsync();
    }
    
    [Authorize]
    [HttpPost]
    public async Task<ActionResult<User>> CreateUser([FromBody] User user) {
        context.Users.Add(user);
        await context.SaveChangesAsync();
        return CreatedAtAction(nameof(CreateUser), new { id = user.Id }, user);
    }
    
    [HttpGet("{userId}")]
    public async Task<ActionResult<User>> GetUserById(ulong userId) {
        User? existingUser = await context.Users.FindAsync(userId);
        if (existingUser == null) {
            return NotFound();
        }
        
        return existingUser;
    }
    
    [Authorize]
    [HttpPut("{userId}")]
    public async Task<ActionResult<User>> UpdateUser(ulong userId, [FromBody] User user) {

        User? existingUser = await context.Users.FindAsync(userId);
        if (existingUser == null) {
            return NotFound();
        }
        
        existingUser.Username = user.Username;
        existingUser.Email = user.Email;
        
        await context.SaveChangesAsync();
        return NoContent();
    }
    
    [Authorize]
    [HttpDelete("{userId}")]
    public async Task<ActionResult<User>> DeleteUser(ulong userId) {
        
        User? existingUser = await context.Users.FindAsync( userId );
        if (existingUser == null) {
            return NotFound();
        }
        
        return context.Users.Remove(existingUser).Entity;
    }
    
    
    
}