const form = document.querySelector('#loanForm');
const assessment = document.querySelector('.assessment');

function value(id) { return document.getElementById(id).value; }
function fallbackAssessment(data) {
  let score = 20, factors = [];
  const ratio = data.loanAmount / Math.max(data.annualIncome, 1);
  if (data.annualIncome < 100000) { score += 20; factors.push('Annual income is below the preferred threshold.'); }
  else if (data.annualIncome < 250000) { score += 10; factors.push('Annual income is moderate for the requested amount.'); }
  else factors.push('Annual farm income supports repayment capacity.');
  if (ratio > 1) { score += 25; factors.push('Requested loan amount is higher than annual income.'); }
  else if (ratio > .5) { score += 15; factors.push('Loan amount is relatively high compared with income.'); }
  else factors.push('Requested amount is within the income comfort range.');
  if (data.existingDebt > data.annualIncome * .5) { score += 20; factors.push('Existing obligations are high compared with income.'); }
  else if (data.existingDebt > 0) { score += 8; factors.push('Existing financial obligations were reported.'); }
  else factors.push('No existing debt has been reported.');
  if (data.missedPayments >= 3) { score += 20; factors.push('Several missed payments were reported.'); }
  else if (data.missedPayments > 0) { score += 10; factors.push('Previous missed payments require review.'); }
  else factors.push('Repayment history shows no missed payments.');
  if (data.weatherRisk === 'high') { score += 15; factors.push('High weather risk is expected for the coming season.'); }
  else if (data.weatherRisk === 'medium') { score += 8; factors.push('Weather outlook needs seasonal monitoring.'); }
  else factors.push('Local weather outlook is currently favourable.');
  score = Math.min(100, score);
  return { riskPercentage: score, riskCategory: score >= 70 ? 'High' : score >= 40 ? 'Medium' : 'Low', assessmentSummary: score < 40 ? 'The available profile shows fewer risk indicators.' : 'Some financial or agricultural factors need review.', suggestedAction: score < 40 ? 'Continue with normal verification process.' : score < 70 ? 'Review financial details and supporting documents.' : 'Verify documents and arrange manual assessment.', riskFactors: factors };
}
function currency(number) { return new Intl.NumberFormat('en-IN', {maximumFractionDigits:0}).format(number); }
function render(result, data) {
  const score = Math.round(result.riskPercentage);
  const risk = result.riskCategory.toLowerCase();
  document.querySelector('#resultName').textContent = value('farmerName');
  document.querySelector('#resultMeta').textContent = `${value('crop')} cultivation · ${value('location')}`;
  const badge = document.querySelector('#riskBadge'); badge.className = `risk-badge ${risk}`; badge.textContent = `${result.riskCategory.toUpperCase()} RISK`;
  document.querySelector('#riskScore').textContent = score;
  document.querySelector('#scoreMeter').style.width = `${score}%`;
  document.querySelector('#scoreSummary').textContent = result.assessmentSummary;
  const comfortablyAffordable = Math.round(data.annualIncome * (result.riskCategory === 'Low' ? .85 : .55));
  const offer = Math.min(data.loanAmount, comfortablyAffordable);
  document.querySelector('#recommendationTitle').textContent = result.riskCategory === 'High' ? 'Needs manual review' : 'Suitable to proceed';
  document.querySelector('#recommendationText').textContent = result.riskCategory === 'High' ? `Verify repayment capacity before a decision. Consider an amount up to ₹${currency(offer)} with safeguards.` : `Recommend up to ₹${currency(offer)} for ${data.tenure} months, subject to document verification.`;
  document.querySelector('#suggestedAction').textContent = result.suggestedAction;
  document.querySelector('#factorList').innerHTML = result.riskFactors.slice(0, 5).map(f => `<li>${f}</li>`).join('');
  assessment.classList.remove('hidden'); assessment.scrollIntoView({behavior:'smooth', block:'start'});
}
form.addEventListener('submit', async (event) => {
  event.preventDefault();
  const button = form.querySelector('.assess-button'); const saved = button.innerHTML;
  button.innerHTML = '<span>◌</span> Analysing profile…'; button.disabled = true;
  const data = { annualIncome:+value('annualIncome'), existingDebt:+value('existingDebt'), loanAmount:+value('loanAmount'), missedPayments:+value('missedPayments'), crop:value('crop'), weatherRisk:value('weatherRisk'), tenure:+value('tenure') };
  try {
    const response = await fetch('/api/assessment/risk', {method:'POST', headers:{'Content-Type':'application/json'}, body:JSON.stringify(data)});
    if (!response.ok) throw new Error('Assessment unavailable');
    render(await response.json(), data);
  } catch (error) { render(fallbackAssessment(data), data); }
  button.innerHTML = saved; button.disabled = false;
});
function approveApplication() { const toast = document.querySelector('#toast'); toast.classList.add('show'); setTimeout(() => toast.classList.remove('show'), 3000); }
