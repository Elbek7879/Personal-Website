// API Configuration
const API_BASE_URL = 'http://localhost:8080/api';

// Helper function for API calls
async function apiCall(endpoint, options = {}) {
    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            },
            ...options
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error('API call failed:', error);
        throw error;
    }
}

// ============= PROJECTS API =============

async function getAllProjects() {
    return apiCall('/projects');
}

async function getFeaturedProjects() {
    return apiCall('/projects/featured');
}

async function getLatestProjects() {
    return apiCall('/projects/latest');
}

async function getProjectById(id) {
    return apiCall(`/projects/${id}`);
}

// ============= SKILLS API =============

async function getAllSkills() {
    return apiCall('/skills');
}

async function getSkillsByCategory(category) {
    return apiCall(`/skills/category/${category}`);
}

// ============= EXPERIENCES API =============

async function getAllExperiences() {
    return apiCall('/experiences');
}

// ============= CONTACT API =============

async function submitContactForm(formData) {
    return apiCall('/contacts', {
        method: 'POST',
        body: JSON.stringify(formData)
    });
}

// ============= LOAD DATA ON PAGE LOAD =============

// Load Projects
async function loadProjects() {
    try {
        const projects = await getFeaturedProjects();
        displayProjects(projects);
    } catch (error) {
        console.error('Failed to load projects:', error);
        showError('Failed to load projects. Please try again later.');
    }
}

// Display Projects
function displayProjects(projects) {
    const container = document.querySelector('.projects-section .row');
    if (!container) return;

    container.innerHTML = '';

    projects.forEach(project => {
        const projectCard = `
            <div class="col-lg-4 col-md-6">
                <div class="project-card">
                    <div class="project-image">
                        <img src="${project.imageUrl}" alt="${project.title}">
                        <div class="project-overlay">
                            <a href="${project.githubUrl}" class="btn btn-light btn-sm me-2" target="_blank">
                                <i class="fab fa-github"></i>
                            </a>
                            ${project.demoUrl ? `
                                <a href="${project.demoUrl}" class="btn btn-primary btn-sm" target="_blank">
                                    <i class="fas fa-external-link-alt"></i>
                                </a>
                            ` : ''}
                        </div>
                    </div>
                    <div class="project-content">
                        <h5>${project.title}</h5>
                        <p>${project.description}</p>
                        <div class="project-tech">
                            ${project.technologies.split(',').map(tech =>
            `<span class="tech-badge">${tech.trim()}</span>`
        ).join('')}
                        </div>
                    </div>
                </div>
            </div>
        `;
        container.innerHTML += projectCard;
    });
}

// Load Skills
async function loadSkills() {
    try {
        const skills = await getAllSkills();
        displaySkills(skills);
    } catch (error) {
        console.error('Failed to load skills:', error);
    }
}

// Display Skills
function displaySkills(skills) {
    const container = document.querySelector('.skills-section .row');
    if (!container) return;

    container.innerHTML = '';

    skills.forEach(skill => {
        const skillCard = `
            <div class="col-lg-3 col-md-6">
                <div class="skill-card">
                    <div class="skill-icon">
                        <i class="${skill.icon}"></i>
                    </div>
                    <h5>${skill.name}</h5>
                    <div class="skill-progress">
                        <div class="progress-bar" style="width: ${skill.level}%"></div>
                    </div>
                    <span class="skill-percentage">${skill.level}%</span>
                </div>
            </div>
        `;
        container.innerHTML += skillCard;
    });
}

// Handle Contact Form Submission
function setupContactForm() {
    const form = document.getElementById('contactForm');
    if (!form) return;

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const submitBtn = form.querySelector('button[type="submit"]');
        const originalText = submitBtn.innerHTML;

        // Show loading
        submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Sending...';
        submitBtn.disabled = true;

        // Get form data
        const formData = {
            name: document.getElementById('name').value,
            email: document.getElementById('email').value,
            phone: document.getElementById('phone')?.value || '',
            subject: document.getElementById('subject')?.value || '',
            message: document.getElementById('message').value
        };

        try {
            const response = await submitContactForm(formData);

            // Show success message
            showSuccessMessage(response.message);

            // Reset form
            form.reset();

        } catch (error) {
            console.error('Failed to submit form:', error);
            showErrorMessage('Failed to send message. Please try again or email me directly.');
        } finally {
            // Restore button
            submitBtn.innerHTML = originalText;
            submitBtn.disabled = false;
        }
    });
}

// Show Success Message
function showSuccessMessage(message) {
    const messageDiv = document.getElementById('formMessage') || createMessageDiv();
    messageDiv.innerHTML = `
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="fas fa-check-circle me-2"></i>${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    `;

    setTimeout(() => {
        messageDiv.innerHTML = '';
    }, 5000);
}

// Show Error Message
function showErrorMessage(message) {
    const messageDiv = document.getElementById('formMessage') || createMessageDiv();
    messageDiv.innerHTML = `
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="fas fa-exclamation-circle me-2"></i>${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    `;
}

// Create Message Div if doesn't exist
function createMessageDiv() {
    const form = document.getElementById('contactForm');
    const div = document.createElement('div');
    div.id = 'formMessage';
    div.className = 'mt-3';
    form.parentNode.appendChild(div);
    return div;
}

// Show Error
function showError(message) {
    console.error(message);
    // You can add a toast notification here
}

// ============= INITIALIZE ON PAGE LOAD =============

document.addEventListener('DOMContentLoaded', () => {
    console.log('🚀 Initializing API connections...');

    // Check which page we're on and load appropriate data
    const currentPage = window.location.pathname.split('/').pop() || 'index.html';

    if (currentPage === 'index.html' || currentPage === '') {
        loadProjects();
        loadSkills();
    }

    if (currentPage === 'projects.html') {
        loadProjects();
    }

    if (currentPage === 'contact.html') {
        setupContactForm();
    }

    console.log('✅ API connections initialized!');
});