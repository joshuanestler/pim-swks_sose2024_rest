﻿using Microsoft.EntityFrameworkCore;
using Rest_AspNet.Models;

namespace Rest_AspNet.Data;

public class BlogContext : DbContext {
    
    public DbSet<User> Users { get; set; }
    public DbSet<Post> Posts { get; set; }
    public DbSet<Comment> Comments { get; set; }
    
    public BlogContext(DbContextOptions<BlogContext> options) : base(options) {
    }
}
